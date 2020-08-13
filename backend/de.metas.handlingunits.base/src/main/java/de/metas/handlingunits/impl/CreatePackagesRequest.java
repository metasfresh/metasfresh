/*
 * #%L
<<<<<<< HEAD:misc/services/camel/de-metas-camel-edi/src/main/java/de/metas/edi/esb/desadvexport/stepcom/qualifier/PackagingCode.java
 * de-metas-edi-esb-camel
=======
 * de.metas.handlingunits.base
>>>>>>> origin/intensive_care_uat:backend/de.metas.handlingunits.base/src/main/java/de/metas/handlingunits/impl/CreatePackagesRequest.java
 * %%
 * Copyright (C) 2020 metas GmbH
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

<<<<<<< HEAD:misc/services/camel/de-metas-camel-edi/src/main/java/de/metas/edi/esb/desadvexport/stepcom/qualifier/PackagingCode.java
package de.metas.edi.esb.desadvexport.stepcom.qualifier;

public enum PackagingCode
{
	/** Pallet ISO 1 - 1/1 EURO Pallet */
	ISO1,

	/** Pallet ISO 1 - 1/2 EURO Pallet */
	ISO2,


	ONEW,

	RETR,

	PACK,

	SLPS,

	/** Carton */
	CART,

	BOXS
=======
package de.metas.handlingunits.impl;

import de.metas.inout.InOutId;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
public class CreatePackagesRequest
{
	@NonNull
	InOutId inOutId;

	@NonNull
	ShipperId shipperId;

	boolean processed;

	@Nullable
	List<String> trackingCodes;
>>>>>>> origin/intensive_care_uat:backend/de.metas.handlingunits.base/src/main/java/de/metas/handlingunits/impl/CreatePackagesRequest.java
}
