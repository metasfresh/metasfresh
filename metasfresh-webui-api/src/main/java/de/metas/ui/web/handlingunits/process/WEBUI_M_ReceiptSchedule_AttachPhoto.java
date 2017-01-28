package de.metas.ui.web.handlingunits.process;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;

import org.adempiere.util.Services;
import org.compiere.model.MImage;

import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.ui.web.exceptions.EntityNotFoundException;

/*
 * #%L
 * metasfresh-webui-api
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

public class WEBUI_M_ReceiptSchedule_AttachPhoto extends JavaProcess
{
	@Param(parameterName = "AD_Image_ID", mandatory = true)
	private int p_AD_Image_ID;

	@Override
	protected String doIt() throws Exception
	{
		final I_M_ReceiptSchedule receiptSchedule = getRecord(I_M_ReceiptSchedule.class);

		final MImage adImage = MImage.get(getCtx(), p_AD_Image_ID);
		if (adImage == null || adImage.getAD_Image_ID() <= 0)
		{
			throw new EntityNotFoundException("@NotFound@ @AD_Image_ID@: " + p_AD_Image_ID);
		}

		final String name = getName();
		final byte[] data = adImage.getData();
		final BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
		Services.get(IHUReceiptScheduleBL.class).attachPhoto(receiptSchedule, name, image);

		return MSG_OK;
	}
}
