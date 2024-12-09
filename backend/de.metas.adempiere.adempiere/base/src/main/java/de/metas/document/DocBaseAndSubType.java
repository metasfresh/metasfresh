package de.metas.document;

<<<<<<< HEAD
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
=======
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import de.metas.util.StringUtils;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
<<<<<<< HEAD
public class DocBaseAndSubType
{
	public static DocBaseAndSubType of(@NonNull final String docBaseType)
	{
		return interner.intern(new DocBaseAndSubType(DocBaseType.ofCode(docBaseType), null));
	}

	public static DocBaseAndSubType of(@NonNull final DocBaseType docBaseType)
	{
		return interner.intern(new DocBaseAndSubType(docBaseType, null));
=======
@JsonIncludeProperties({ "docBaseType", "docSubType" })
public class DocBaseAndSubType
{
	public static DocBaseAndSubType of(@NonNull final DocBaseType docBaseType)
	{
		return interner.intern(new DocBaseAndSubType(docBaseType, DocSubType.ANY));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static DocBaseAndSubType of(@NonNull final String docBaseType, @Nullable final String docSubType)
	{
<<<<<<< HEAD
		return interner.intern(new DocBaseAndSubType(DocBaseType.ofCode(docBaseType), docSubType));
=======
		return interner.intern(new DocBaseAndSubType(DocBaseType.ofCode(docBaseType), DocSubType.ofNullableCode(docSubType)));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	public static DocBaseAndSubType of(@NonNull final DocBaseType docBaseType, @Nullable final String docSubType)
	{
<<<<<<< HEAD
		return interner.intern(new DocBaseAndSubType(docBaseType, docSubType));
	}

	private static final Interner<DocBaseAndSubType> interner = Interners.newStrongInterner();

	@NonNull DocBaseType docBaseType;
	@Nullable String docSubType;

	private DocBaseAndSubType(
			@NonNull final DocBaseType docBaseType,
			@Nullable final String docSubType)
=======
		return interner.intern(new DocBaseAndSubType(docBaseType, DocSubType.ofNullableCode(docSubType)));
	}

	public static DocBaseAndSubType of(@NonNull final DocBaseType docBaseType, @NonNull final DocSubType docSubType)
	{
		return interner.intern(new DocBaseAndSubType(docBaseType, docSubType));
	}

	@Nullable
	public static DocBaseAndSubType ofNullable(
			@Nullable final String docBaseType,
			@Nullable final String docSubType)
	{
		final String docBaseTypeNorm = StringUtils.trimBlankToNull(docBaseType);
		return docBaseTypeNorm != null ? of(docBaseTypeNorm, docSubType) : null;
	}

	private static final Interner<DocBaseAndSubType> interner = Interners.newStrongInterner();

	@NonNull DocBaseType docBaseType;
	@NonNull DocSubType docSubType;

	private DocBaseAndSubType(
			@NonNull final DocBaseType docBaseType,
			@NonNull final DocSubType docSubType)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		this.docBaseType = docBaseType;
		this.docSubType = docSubType;
	}

<<<<<<< HEAD
}
=======
	// DocBaseAndSubTypeChecks
	public boolean isSalesInvoice() {return docBaseType.isSalesInvoice() && docSubType.isNone();}


	public boolean isPrepaySO() {return docBaseType.isSalesOrder() && docSubType.isPrepay();}


	public boolean isCallOrder() {return (docBaseType.isSalesOrder() || docBaseType.isPurchaseOrder()) && docSubType.isCallOrder();}

	public boolean isFrameAgreement() { return ( docBaseType.isSalesOrder() || docBaseType.isPurchaseOrder() ) && docSubType.isFrameAgreement(); }

	public boolean isMediated() {return (docBaseType.isPurchaseOrder()) && docSubType.isMediated();}

	public boolean isRequisition() {return (docBaseType.isPurchaseOrder()) && docSubType.isRequisition();}

}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
