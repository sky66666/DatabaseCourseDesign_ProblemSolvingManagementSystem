package QuestionBox;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class UnavaliableFilter {

    protected Shell shell;
    String s;

    public static void main(String[] args){
        try {
            UnavaliableFilter window = new UnavaliableFilter();
            window.open("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open(String s){
        this.s = s;
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
        shell.setSize(240, 120);
        shell.setText("Error:006");
        shell.setLocation(Display.getCurrent().getClientArea().width / 2 - shell.getShell().getSize().x / 2, Display.getCurrent()
                .getClientArea().height / 2 - shell.getSize().y / 2);

        Label label = new Label(shell, SWT.CENTER);
        label.setText(s+" 的数据格式不合法!");
        label.setBounds(20, 28, 180, 23);

    }
}
