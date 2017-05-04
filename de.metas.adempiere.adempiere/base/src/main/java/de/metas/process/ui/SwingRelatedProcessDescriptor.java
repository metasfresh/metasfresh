package de.metas.process.ui;

import java.util.function.Supplier;

import javax.swing.Icon;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.images.Images;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.model.I_AD_Process;
import org.compiere.model.MTreeNode;
import org.compiere.util.Env;

import com.google.common.base.MoreObjects;

import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RelatedProcessDescriptor;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class SwingRelatedProcessDescriptor
{
	public static final SwingRelatedProcessDescriptor of( //
			final RelatedProcessDescriptor relatedProcessDescriptor //
			, final Supplier<ProcessPreconditionsResolution> preconditionsResolutionSupplier //
	)
	{
		return new SwingRelatedProcessDescriptor(relatedProcessDescriptor, preconditionsResolutionSupplier);
	}

	private final int adProcessId;
	private final Supplier<I_AD_Process> adProcessSupplier;
	private final Supplier<ProcessPreconditionsResolution> preconditionsResolutionSupplier;

	private SwingRelatedProcessDescriptor( //
			final RelatedProcessDescriptor relatedProcessDescriptor //
			, final Supplier<ProcessPreconditionsResolution> preconditionsResolutionSupplier //
	)
	{
		super();

		Check.assumeNotNull(relatedProcessDescriptor, "Parameter relatedProcessDescriptor is not null");

		adProcessId = relatedProcessDescriptor.getAD_Process_ID();
		adProcessSupplier = ExtendedMemorizingSupplier.of(() -> InterfaceWrapperHelper.create(Env.getCtx(), adProcessId, I_AD_Process.class, ITrx.TRXNAME_None));

		this.preconditionsResolutionSupplier = ExtendedMemorizingSupplier.ofJUFSupplier(preconditionsResolutionSupplier);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("AD_Process_ID", adProcessId)
				.toString();
	}

	public int getAD_Process_ID()
	{
		return adProcessId;
	}

	public String getCaption(final String adLanguage)
	{
		String caption = getPreconditionsResolution().getCaptionOverrideOrNull(adLanguage);

		if (caption == null)
		{
			final I_AD_Process adProcess = adProcessSupplier.get();
			final I_AD_Process adProcessTrl = InterfaceWrapperHelper.translate(adProcess, I_AD_Process.class, adLanguage);

			caption = adProcessTrl.getName();
		}

		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			final I_AD_Process adProcess = adProcessSupplier.get();
			caption += "/" + adProcess.getValue();
		}

		return caption;
	}

	public String getDescription(final String adLanguage)
	{
		final I_AD_Process adProcess = adProcessSupplier.get();
		final I_AD_Process adProcessTrl = InterfaceWrapperHelper.translate(adProcess, I_AD_Process.class, adLanguage);

		String description = adProcessTrl.getDescription();

		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			if (description == null)
			{
				description = "";
			}
			else
			{
				description += "\n - ";
			}
			description += "Classname:" + adProcess.getClassname() + ", ID=" + adProcess.getAD_Process_ID();
		}

		return description;
	}

	public String getHelp(final String adLanguage)
	{
		final I_AD_Process adProcess = adProcessSupplier.get();
		final I_AD_Process adProcessTrl = InterfaceWrapperHelper.translate(adProcess, I_AD_Process.class, adLanguage);

		return adProcessTrl.getHelp();
	}

	public Icon getIcon()
	{
		final I_AD_Process adProcess = adProcessSupplier.get();

		final String iconName;
		if (adProcess.getAD_Form_ID() > 0)
		{
			iconName = MTreeNode.getIconName(MTreeNode.TYPE_WINDOW);
		}
		else if (adProcess.getAD_Workflow_ID() > 0)
		{
			iconName = MTreeNode.getIconName(MTreeNode.TYPE_WORKFLOW);
		}
		else if (adProcess.isReport())
		{
			iconName = MTreeNode.getIconName(MTreeNode.TYPE_REPORT);
		}
		else
		{
			iconName = MTreeNode.getIconName(MTreeNode.TYPE_PROCESS);
		}

		return Images.getImageIcon2(iconName);
	}

	private ProcessPreconditionsResolution getPreconditionsResolution()
	{
		return preconditionsResolutionSupplier.get();
	}

	public boolean isEnabled()
	{
		return getPreconditionsResolution().isAccepted();
	}
	
	public boolean isSilentRejection()
	{
		return getPreconditionsResolution().isInternal();
	}

	public boolean isDisabled()
	{
		return getPreconditionsResolution().isRejected();
	}

	public String getDisabledReason(final String adLanguage)
	{
		return getPreconditionsResolution().getRejectReason();
	}
}
