package de.metas.handlingunits.client.editor.hu.view.context.menu;

public interface ICMActionGroup extends Comparable<ICMActionGroup>
{
	int SEQNO_Top = 1000;
	int SEQNO_Middle = 5000;
	int SEQNO_Bottom = 100000;

	String getId();

	String getName();

	/**
	 * 
	 * @return sort order sequence number
	 */
	int getSeqNo();
}
