package de.metas.product.impexp;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.compiere.model.I_I_Product;

import java.util.Collection;

public class CompositeProductImportProcessListener implements ProductImportProcessListener
{
	private final ImmutableList<ProductImportProcessListener> listeners;

	private CompositeProductImportProcessListener(@NonNull final ImmutableList<ProductImportProcessListener> listeners)
	{
		this.listeners = listeners;
	}

	public static CompositeProductImportProcessListener ofCollection(@NonNull final Collection<ProductImportProcessListener> listeners)
	{
		return new CompositeProductImportProcessListener(ImmutableList.copyOf(listeners));
	}

	@Override
	public void afterRecordImport(final @NonNull I_I_Product importRecord)
	{
		listeners.forEach(listener -> listener.afterRecordImport(importRecord));
	}
}
