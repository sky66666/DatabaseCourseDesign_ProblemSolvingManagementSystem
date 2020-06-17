import Utils.dbConstant;
import Utils.dbController;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import org.eclipse.swt.SWT;

import java.sql.ResultSet;
import java.sql.SQLException;

import Dialog.*;
import Utils.*;

public class login {

    protected Shell shell;
    private dbController db;

    public static void main(String[] args){
        try {
            login window = new login();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        try {
            db = new dbController(dbConstant.DBURL,dbConstant.USERNAME,dbConstant.PASSWORD);
        }catch (SQLException e){
            e.printStackTrace();
        }
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    protected void createContents() {
        shell = new Shell();
        shell.setSize(300, 200);
        shell.setText("用户登录界面");
        shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x / 2, Display.getCurrent()
                .getClientArea().height / 2 - shell.getSize().y / 2);

        Label label1 = new Label(shell, SWT.CENTER);
        label1.setText("用户名");
        label1.setBounds(30,30,38,23);

        Label label2 = new Label(shell, SWT.CENTER);
        label2.setText("密码");
        label2.setBounds(30,60,38,23);

        Text text1 = new Text(shell, SWT.BORDER);
        text1.setBounds(72, 28, 180, 23);

        Text text2 = new Text(shell, SWT.BORDER);
        text2.setBounds(72, 58, 180, 23);

        Button button1 = new Button(shell, SWT.NONE);
        button1.setBounds(40,100,80,30);
        button1.setText("登录");
        button1.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String username = text1.getText();
                String password = text2.getText();
                if(username.equals("")||password.equals("")){
                    EmptyUserInfo win = new EmptyUserInfo();
                    win.open();
                }else if (username.length()>20||password.length()>20){
                    OversizeUserInfo window = new OversizeUserInfo();
                    window.open();
                } else{
                    ResultSet res = db.query("select * from users where username = '"+username+"' ");
                    try {
                        if(res.next()){
                            if(res.getString("password").equals(password)){
                                //successfully login here
                                shell.setVisible(false);
                                menu nxWin = new menu();
                                nxWin.open(res.getString("level").equals("管理员"),res.getString("username"),res.getInt("id"));
                                shell.setVisible(true);
                            }else{
                                WrongPassword win = new WrongPassword();
                                win.open();
                            }
                        }else{
                            NonExistingUsername win = new NonExistingUsername();
                            win.open();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        Button button2 = new Button(shell, SWT.NONE);
        button2.setBounds(160,100,80,30);
        button2.setText("注册");
        button2.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String username = text1.getText();
                String password = text2.getText();
                if(username.equals("")||password.equals("")){
                    EmptyUserInfo win = new EmptyUserInfo();
                    win.open();
                }else if (username.length()>20||password.length()>20){
                    OversizeUserInfo window = new OversizeUserInfo();
                    window.open();
                } else{
                    ResultSet res = db.query(String.format("select * from users where username = '%s' ",username));
                    try {
                        if(res.next()){
                            ExistingUsername win = new ExistingUsername();
                            win.open();
                        }else{
                            db.addItem(String.format("insert into users values(null,'%s','%s','用户')",username,password));
                            SuccessfulOperation window = new SuccessfulOperation();
                            window.open();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }
}
