package main.enums;

import org.eclipse.swt.SWT;

public enum Styles {
	SHELL_SIZE(SWT.SHELL_TRIM & (~SWT.RESIZE)), //
	INPUT_FIELD_FONT_SIZE(13); //

	private int style;

	private Styles(int style) {
		this.style = style;
	}

	public int getStyle() {
		return style;
	}
}
