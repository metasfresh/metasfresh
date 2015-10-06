package de.metas.handlingunits.client.editor.command.model.action;

public interface IAction
{
	String getActionName();

	void doIt();

	void undoIt();
}
