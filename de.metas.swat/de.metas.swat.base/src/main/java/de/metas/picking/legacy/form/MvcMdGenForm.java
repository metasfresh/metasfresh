package de.metas.picking.legacy.form;

import org.compiere.minigrid.IMiniTable;
import org.compiere.print.MPrintFormat;

public class MvcMdGenForm {
	
	private boolean m_selectionActive = true;
	private String title;
	private int reportEngineType;
	private MPrintFormat printFormat = null;
	private String askPrintMsg;

	private IMiniTable miniTable;
	
	private final int m_WindowNo;
	
	public boolean uiLocked;
	
	public MvcMdGenForm(final int windowNo)
	{
		super();
		this.m_WindowNo = windowNo;
	}
	
	public void setMiniTable(final IMiniTable miniTable)
	{
		this.miniTable = miniTable;
	}

	public boolean isSelectionActive() {
		return m_selectionActive;
	}

	public void setSelectionActive(boolean active) {
		m_selectionActive = active;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getReportEngineType() {
		return reportEngineType;
	}

	public void setReportEngineType(int reportEngineType) {
		this.reportEngineType = reportEngineType;
	}

	public MPrintFormat getPrintFormat() {
		return this.printFormat;
	}

	public void setPrintFormat(MPrintFormat printFormat) {
		this.printFormat = printFormat;
	}

	public String getAskPrintMsg() {
		return askPrintMsg;
	}

	public void setAskPrintMsg(String askPrintMsg) {
		this.askPrintMsg = askPrintMsg;
	}

	@SuppressWarnings("unchecked")
	public <T extends IMiniTable> T getMiniTable() {
		return (T)miniTable;
	}

	public int getWindowNo() {
		return m_WindowNo;
	}
}
