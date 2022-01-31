package main.customs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import main.enums.ControlDimensions;
import main.enums.Styles;

public class InputField {
	private Composite parent;

	private Label fieldName;
	private Text textField;

	public InputField(Composite shell, int style, ControlDimensions width, ControlDimensions height, boolean title) {
		this.parent = new Composite(shell, SWT.NONE);
		this.parent.setLayout(new GridLayout(1, false));

		if (title) {
			fieldName = new Label(parent, SWT.NONE);
		}
		textField = new Text(parent, style);

		textField.setLayoutData(new GridData(width.getValue(), height.getValue()));

		FontData[] font = textField.getFont().getFontData();
		font[0].setHeight(Styles.INPUT_FIELD_FONT_SIZE.getStyle());
		textField.setFont(new Font(textField.getDisplay(), font[0]));
	}

	public void setText(String text) {
		fieldName.setText(text);
	}

	public String getText() {
		return textField.getText();
	}

	public Text getField() {
		return textField;
	}
}
