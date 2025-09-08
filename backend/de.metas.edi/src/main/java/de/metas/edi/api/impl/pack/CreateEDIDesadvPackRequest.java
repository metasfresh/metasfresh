/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.edi.api.impl.pack;

import de.metas.edi.api.EDIDesadvId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.generichumodel.PackagingCodeId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
public class CreateEDIDesadvPackRequest
{
	@NonNull
	OrgId orgId;

	@NonNull
	EDIDesadvId ediDesadvId;

	/**
	 * A value <=0 means that the repo shall pick the proper seqNo
	 */
	@NonNull
	Integer seqNo;
	
	@NonNull
	String sscc18;

	/**
	 * true means the SSCC was just created on-the-fly. false means it's coming from a HU's SSCC-Attribute.
	 */
	@NonNull
	Boolean isManualIpaSSCC;

	@Nullable
	HuId huId;

	@Nullable
	PackagingCodeId huPackagingCodeID;

	@Nullable
	String gtinPackingMaterial;

	@NonNull
	@Singular
	List<CreateEDIDesadvPackItemRequest> createEDIDesadvPackItemRequests;

}
