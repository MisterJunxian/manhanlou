package com.liujunxian.manhanlou.view;

import com.liujunxian.manhanlou.domain.Employee;
import com.liujunxian.manhanlou.domain.Table;
import com.liujunxian.manhanlou.service.EmployeeService;
import com.liujunxian.manhanlou.service.TableService;
import com.liujunxian.manhanlou.utils.Utility;

import java.util.List;

public class AppView {
    /**
     * 控制是否退出菜单
     */
    private boolean loop = true;
    /**
     * 接收用户输入
     */
    private String key;
    
    public static void main(String[] args) {
        new AppView().mainMenu();
    }
    
    /**
     * 显示主菜单
     */
    public void mainMenu() {
        while (loop) {
            System.out.println("===============满汉楼===============");
            System.out.println("\t\t1. 登录满汉楼");
            System.out.println("\t\t2. 退出满汉楼");
            System.out.print("请输入你的选择：");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.print("请输入工号：");
                    String ID = Utility.readString(12);
                    System.out.print("请输入密码：");
                    String password = Utility.readString(18);
                    Employee employee = EmployeeService.getEmployee(ID, password);
                    if (employee != null) {
                        System.out.println("===============登录成功" + employee.getName() + "===============\n");
                        EnterContinue();
                        while (loop) {
                            System.out.println("===============满汉楼===============");
                            System.out.println("\t\t1. 显示餐桌状态");
                            System.out.println("\t\t2. 预定餐桌");
                            System.out.println("\t\t3. 显示所有菜品");
                            System.out.println("\t\t4. 点餐服务");
                            System.out.println("\t\t5. 查看账单");
                            System.out.println("\t\t6. 结账");
                            System.out.println("\t\t9. 退出满汉楼");
                            System.out.print("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    tableList();
                                    EnterContinue();
                                    break;
                                case "2":
                                    bookTable();
                                    EnterContinue();
                                    break;
                                case "3":
                                    EnterContinue();
                                    break;
                                case "4":
                                    EnterContinue();
                                    break;
                                case "5":
                                    EnterContinue();
                                    break;
                                case "6":
                                    EnterContinue();
                                    break;
                                case "9":
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("输入有误，请重新输入！");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("===============工号或密码错误，请重试！===============");
                        EnterContinue();
                    }
                    break;
                case "2":
                    loop = false;
                    break;
                default:
                    System.out.println("输入有误，请重新输入！");
                    break;
            }
        }
        System.out.println("退出满汉楼系统~");
    }
    
    /**
     * 提示“按下 Enter 继续”并监听用户输入
     */
    private static void EnterContinue() {
        System.out.print("按下 Enter 继续~");
        Utility.readString(20, "继续");
    }
    
    /**
     * 显示餐桌状态
     */
    private static void tableList() {
        List<Table> list = TableService.list();
        System.out.println("==============================");
        System.out.println("\t餐桌号\t\t\t状态");
        for (Table table : list) {
            System.out.println(table);
        }
        System.out.println("==============================");
    }
    
    private static void bookTable() {
        System.out.println("==========预定餐桌==========");
        boolean isLoop = true;
        while (isLoop) {
            System.out.print("请选择要预定的餐桌编号(-1退出)：");
            int key = Utility.readInt();
            switch (key) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    if (!TableService.isEmpty(key)) {
                        System.out.println("该餐桌不是空闲餐桌！");
                        break;
                    }
                    System.out.print("预定人姓名：");
                    String name = Utility.readString(20);
                    System.out.print("预定人电话：");
                    String phone = Utility.readString(12);
                    System.out.print("确认是否预定(Y:确定 | 其他任意键取消)：");
                    char c = Utility.readChar();
                    if ('y' == c || 'Y' == c)
                        System.out.println(TableService.book(key, name, phone) ? "\n---预定成功" : "\n---预定失败");
                    isLoop = false;
                    break;
                case -1:
                    System.out.print("取消预定\t");
                    break;
                default:
                    System.out.println("请输入正确的餐桌编号！");
                    break;
            }
        }
    }
}
