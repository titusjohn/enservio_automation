package enservio.framework.testdatafunctions;

import enservio.framework.errorhandling.*;

public class ExcelCellFormatting {
	private String fontName;
	private short fontSize;
	private short backColorIndex;
	private short foreColorIndex;
	public Boolean bold = Boolean.valueOf(false);

	public Boolean italics = Boolean.valueOf(false);

	public Boolean centred = Boolean.valueOf(false);

	public String getFontName() {
		return this.fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public short getFontSize() {
		return this.fontSize;
	}

	public void setFontSize(short fontSize) {
		this.fontSize = fontSize;
	}

	public short getBackColorIndex() {
		return this.backColorIndex;
	}

	public void setBackColorIndex(short backColorIndex) {
		if ((backColorIndex < 8) || (backColorIndex > 64)) {
			throw new setupexceptions(
					"Valid indexes for the Excel custom palette are from 0x8 to 0x40 (inclusive)!");
		}

		this.backColorIndex = backColorIndex;
	}

	public short getForeColorIndex() {
		return this.foreColorIndex;
	}

	public void setForeColorIndex(short foreColorIndex) {
		if ((foreColorIndex < 8) || (foreColorIndex > 64)) {
			throw new setupexceptions(
					"Valid indexes for the Excel custom palette are from 0x8 to 0x40 (inclusive)!");
		}

		this.foreColorIndex = foreColorIndex;
	}
}
