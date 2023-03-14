package org.adempiere.ad.wrapper;

public interface POJONextIdSupplier
{
	int nextId(String tableName);

	void reset();
}
