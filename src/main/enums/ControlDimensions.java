package main.enums;

public enum ControlDimensions {
	INPUT_FIELD_WIDTH(400), //
	INPUT_FIELD_HEIGHT(25);

	private int value;

	private ControlDimensions(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
