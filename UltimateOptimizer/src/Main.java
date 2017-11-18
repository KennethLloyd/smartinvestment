import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class Main {

	protected Shell shlUltimateOptimizer;
	private Text textAreaZ;
	private Text con1a;
	private Text con1b;
	private Text con2a;
	private Text con2b;
	private Text con3a;
	private Text con3b;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Main window = new Main();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlUltimateOptimizer.open();
		shlUltimateOptimizer.layout();
		while (!shlUltimateOptimizer.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlUltimateOptimizer = new Shell();
		shlUltimateOptimizer.setSize(450, 300);
		shlUltimateOptimizer.setText("Ultimate Optimizer");
		
		Label labelZ = new Label(shlUltimateOptimizer, SWT.NONE);
		labelZ.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.NORMAL));
		labelZ.setBounds(38, 10, 34, 23);
		labelZ.setText("z =");
		
		textAreaZ = new Text(shlUltimateOptimizer, SWT.BORDER);
		textAreaZ.setBounds(73, 10, 215, 21);
		
		Label labelConstraints = new Label(shlUltimateOptimizer, SWT.NONE);
		labelConstraints.setFont(SWTResourceManager.getFont("Segoe UI", 13, SWT.NORMAL));
		labelConstraints.setBounds(38, 53, 115, 23);
		labelConstraints.setText("Constraints");
		
		con1a = new Text(shlUltimateOptimizer, SWT.BORDER);
		con1a.setBounds(48, 82, 66, 21);
		
		Combo combo1 = new Combo(shlUltimateOptimizer, SWT.NONE);
		combo1.setItems(new String[] {">=", "<="});
		combo1.setBounds(120, 82, 46, 21);
		
		con1b = new Text(shlUltimateOptimizer, SWT.BORDER);
		con1b.setBounds(172, 82, 66, 21);
		
		con2a = new Text(shlUltimateOptimizer, SWT.BORDER);
		con2a.setBounds(48, 110, 66, 21);
		
		Combo combo2 = new Combo(shlUltimateOptimizer, SWT.NONE);
		combo2.setItems(new String[] {">=", "<="});
		combo2.setBounds(120, 108, 46, 23);
		
		con2b = new Text(shlUltimateOptimizer, SWT.BORDER);
		con2b.setBounds(172, 109, 66, 21);
		
		con3a = new Text(shlUltimateOptimizer, SWT.BORDER);
		con3a.setBounds(48, 137, 66, 21);
		
		Combo combo3 = new Combo(shlUltimateOptimizer, SWT.NONE);
		combo3.setItems(new String[] {">=", "<="});
		combo3.setBounds(120, 137, 46, 23);
		
		con3b = new Text(shlUltimateOptimizer, SWT.BORDER);
		con3b.setBounds(172, 136, 66, 21);
		
		Button btnMaximize = new Button(shlUltimateOptimizer, SWT.RADIO);
		btnMaximize.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnMaximize.setBounds(63, 191, 90, 16);
		btnMaximize.setText("Maximize");
		
		Button btnMinimize = new Button(shlUltimateOptimizer, SWT.RADIO);
		btnMinimize.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnMinimize.setText("Minimize");
		btnMinimize.setBounds(164, 191, 90, 16);
		
		Button btnSubmit = new Button(shlUltimateOptimizer, SWT.NONE);
		btnSubmit.setBounds(48, 226, 75, 25);
		btnSubmit.setText("Submit");

	}
}
