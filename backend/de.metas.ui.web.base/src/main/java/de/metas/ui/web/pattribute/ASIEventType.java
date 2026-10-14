package de.metas.ui.web.pattribute;

public enum ASIEventType
{
	NEW,
	COPY,
	VALUE_CHANGED,
	;

	public boolean isInitializing() {return this == NEW || this == COPY;}
}
