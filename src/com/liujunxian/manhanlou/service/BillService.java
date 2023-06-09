package com.liujunxian.manhanlou.service;

import com.liujunxian.manhanlou.dao.BillDAO;
import com.liujunxian.manhanlou.dao.MultiDAO;
import com.liujunxian.manhanlou.domain.Bill;
import com.liujunxian.manhanlou.domain.Menu;
import com.liujunxian.manhanlou.domain.Multi;
import com.liujunxian.manhanlou.domain.Table;

import java.util.List;
import java.util.UUID;

public class BillService {
    private static final BillDAO billDAO = new BillDAO();
    private static final MultiDAO multiDAO = new MultiDAO();
    
    /**
     * 订餐
     *
     * @param tableID 餐桌ID
     * @param menuID  菜品ID
     * @param num     菜品数量
     */
    public static void order(Table table, int tableID, int menuID, int num) {
        String billID = UUID.randomUUID().toString();
        String sql = "insert into `bill` values(?, ?, ?, ?, ?, now(), '未支付')";
        Menu menu = MenuService.getMenu(menuID);
        billDAO.update(sql, billID, menuID, num, menu.getPrice() * num, tableID);
        TableService.updateTableState(table, tableID, "用餐中");
    }
    
    /**
     * 订餐
     *
     * @param tableID  餐桌ID
     * @param menuName 菜品名
     * @param num      菜品数量
     */
    public static void order(Table table, int tableID, String menuName, int num) {
        String billID = UUID.randomUUID().toString();
        String sql = "insert into `bill` values(?, ?, ?, ?, ?, now(), '未支付')";
        Menu menu = MenuService.getMenu(menuName);
        billDAO.update(sql, billID, menu.getID(), num, menu.getPrice() * num, tableID);
        TableService.updateTableState(table, tableID, "用餐中");
    }
    
    /**
     * 查询所有未结账账单
     *
     * @return 以List实例返回所有未结账账单信息
     */
    public static List<Bill> unfinishedList() {
        String sql = "select * from `bill` where state = '未支付'";
        return billDAO.queryMulti(sql, Bill.class);
    }
    
    /**
     * 查询所有未结账账单，显示菜名
     *
     * @return 以List实例返回所有未结账账单信息
     */
    public static List<Multi> unfinishedList2() {
        String sql = "select bill.*, name from bill, menu where bill.menuID = menu.id and state = '未支付'";
        return multiDAO.queryMulti(sql, Multi.class);
    }
    
    /**
     * 查询所有往期账单
     *
     * @return 以List实例返回所有往期账单信息
     */
    public static List<Bill> finishedList() {
        String sql = "select * from `bill` where state != '未支付'";
        return billDAO.queryMulti(sql, Bill.class);
    }
    
    /**
     * 查询所有往期账单，显示菜名
     *
     * @return 以List实例返回所有往期账单信息
     */
    public static List<Multi> finishedList2() {
        String sql = "select bill.*, name from bill, menu where bill.menuID = menu.id and state != '未支付'";
        return multiDAO.queryMulti(sql, Multi.class);
    }
    
    /**
     * 结账
     * @param tableID 餐桌ID
     * @param way 结账方式（现金/支付宝/微信）
     */
    public static void pay(Integer tableID, String way) {
        TableService.updateTableState(tableID, "空闲");
        String sql = "update `bill` set state = ? where tableID = ?";
        billDAO.update(sql, way, tableID);
    }
}
