import Dialog.*;
import QuestionBox.Filter;
import QuestionBox.Insert;
import Utils.dbConstant;
import Utils.dbController;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class menu {

    protected Shell shell;
    private dbController db;
    Table table;
    TableItem item;

    private void query(String sql) throws SQLException {
        ResultSet res = db.query(sql);
        TableItem[] tableItems = table.getItems();//得到所有的tableItem
        for(int i = 0; i<tableItems.length; i++){
            tableItems[i].dispose();//释放
        }
        while(res.next()) {
            TableItem tmpItem = new TableItem(table, SWT.NONE);
            String[] item = new String[6];
            for (int i = 1; i <= 6; ++i) {
                item[i-1] = res.getString(i);
            }
            item[5] = item[5].substring(0,11);
            tmpItem.setText(item);
        }
    }

    boolean isAdmin;
    String username;
    int userid;

    public static void main(String[] args){
        try {
            menu window = new menu();
            window.open(false,"root",1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open(boolean isAdmin,String username,int userid) throws SQLException {
        this.isAdmin = isAdmin;
        this.username = username;
        this.userid = userid;
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
        shell.setSize(780, 800);
        shell.setText("主界面");
        shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x / 2, Display.getCurrent()
                .getClientArea().height / 2 - shell.getSize().y / 2);

        table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        table.setBounds(20, 120, 724, 590);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        final String[] columnTag = {"测评号","用户编号","题号","测评结果","测评耗时","测评日期"};
        for(int i=0;i<6;++i){
            TableColumn tableColumn = new TableColumn(table,SWT.NONE);
            tableColumn.setText(columnTag[i]);
            tableColumn.setWidth(120);
        }

        Label l1 = new Label(shell,SWT.LEFT);
        l1.setBounds(600,15,60,27);
        l1.setText("题目编号：");

        Label l2 = new Label(shell,SWT.LEFT);
        l2.setBounds(600,50,60,27);
        l2.setText("题目名称：");

        Label l3 = new Label(shell,SWT.LEFT);
        l3.setBounds(600,85,60,27);
        l3.setText("题目难度：");

        Label l4 = new Label(shell,SWT.LEFT);
        l4.setBounds(660,15,100,27);

        Label l5 = new Label(shell,SWT.LEFT);
        l5.setBounds(660,50,100,27);

        Label l6 = new Label(shell,SWT.LEFT);
        l6.setBounds(660,85,100,27);

        Text t1 =new Text(shell,SWT.BORDER);
        t1.setBounds(28,720,110,28);
        t1.setEditable(false);

        Text t2 =new Text(shell,SWT.BORDER);
        t2.setBounds(148,720,110,28);

        Text t3 =new Text(shell,SWT.BORDER);
        t3.setBounds(268,720,110,28);

        Button b1 = new Button(shell,SWT.NONE);
        b1.setBounds(388,720,110,28);
        b1.setText("修改");
        b1.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if(db.addItem(String.format("update solvements set id=%s,userid=%s,problemid=%s where id=%s"
                        ,t1.getText(),t2.getText(),t3.getText(),t1.getText()))){
                    item.setText(0,t1.getText());
                    item.setText(1,t2.getText());
                    item.setText(2,t3.getText());
                }
            }
        });

        Button b2 = new Button(shell,SWT.NONE);
        b2.setBounds(508,720,110,28);
        b2.setText("删除");
        b2.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if(db.removeItem(String.format("delete from solvements where id=%s",t1.getText()))) {
                    item.dispose();
                }
            }
        });

        Button b3 = new Button(shell,SWT.NONE);
        b3.setBounds(628,720,110,28);
        b3.setText("退回登录");
        b3.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.dispose();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClick(MouseEvent e) {
                super.mouseDoubleClick(e);
                int row = table.getSelectionIndex();
                if(row>=table.getItems().length||row==-1){
                    System.out.println(row);
                    System.out.println(table.getItems().length);
                    return;
                }
                item = table.getItems()[row];
                ResultSet res =db.query("select * from problems where id = "+item.getText(2));
                try {
                    res.next();
                    l4.setText(res.getString(1));
                    l5.setText(res.getString(2));
                    l6.setText(res.getString(3));
                    t1.setText(item.getText(0));
                    t2.setText(item.getText(1));
                    t3.setText(item.getText(2));
                    if(isAdmin||item.getText(1).equals(String.valueOf(userid))){
                        t2.setEditable(true);
                        t3.setEditable(true);
                        b1.setEnabled(true);
                        b2.setEnabled(true);
                    }else{
                        t2.setEditable(false);
                        t3.setEditable(false);
                        b1.setEnabled(false);
                        b2.setEnabled(false);
                    }
                    if(isAdmin){
                        t2.setEditable(true);
                    }else{
                        t2.setEditable(false);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        Label label = new Label(shell,SWT.LEFT);
        label.setText("欢迎您, "+ (isAdmin?"管理员":"用户") +" " + username + " !");
        label.setBounds(20,15,180,27);

        Button button1 = new Button(shell,SWT.NONE);
        button1.setText("所有记录");
        button1.setBounds(20,45,80,27);
        button1.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    query(String.format("select * from solvements"));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button button2 = new Button(shell,SWT.NONE);
        button2.setText("我的记录");
        button2.setBounds(20,80,80,27);
        button2.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                try {
                    query(String.format("select * from solvements where userid=%s",userid));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button button3 = new Button(shell,SWT.NONE);
        button3.setText("条件筛选");
        button3.setBounds(120,45,80,27);
        button3.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                StringBuilder sql = new StringBuilder("select * from solvements ");
                Filter qBox = new Filter();
                qBox.avaliable=false;
                qBox.open();
                boolean flag=false;
                if(qBox.avaliable){
                    if(!qBox.solveid.equals("")){
                        if(!flag){
                            flag=true;
                            sql.append("where ");
                        }else{
                            sql.append(" and ");
                        }
                        sql.append("id=");
                        sql.append(qBox.solveid);
                    }
                    if(!qBox.userid.equals("")){
                        if(!flag){
                            flag=true;
                            sql.append("where ");
                        }else{
                            sql.append(" and ");
                        }
                        sql.append("userid=");
                        sql.append(qBox.userid);
                    }
                    if(!qBox.problemid.equals("")){
                        if(!flag){
                            flag=true;
                            sql.append("where ");
                        }else{
                            sql.append(" and ");
                        }
                        sql.append("problemid=");
                        sql.append(qBox.problemid);
                    }
                    if(!qBox.result.equals("")){
                        if(!flag){
                            flag=true;
                            sql.append("where ");
                        }else{
                            sql.append(" and ");
                        }
                        sql.append("result='");
                        sql.append(qBox.result);
                        sql.append("'");
                    }

                    try {
                        query(sql.toString());
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        Button button4 = new Button(shell,SWT.NONE);
        button4.setText("插入数据");
        button4.setBounds(120,80,80,27);
        button4.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {

                Insert qBox = new Insert();
                qBox.avaliable = false;
                qBox.open(isAdmin,String.valueOf(userid));

                if(!qBox.avaliable)return;

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                db.addItem(String.format("insert into solvements values(null,%s,%s,'%s',%s,'%s')",
                        qBox.userid,qBox.problemid,qBox.result,qBox.cost,df.format(new Date())));

                try {
                    query("select * from solvements");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
}
