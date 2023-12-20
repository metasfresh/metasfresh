/*
 * #%L
 * de-metas-camel-sap
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.camel.externalsystems.sap.export.generalledger.lobservices;

import com.google.common.collect.ImmutableList;

public interface Format
{
	String NAMESPACE_PREFIX = "ns0";
	String NAMESPACE_URI = "http://Microsoft.LobServices.Sap/2007/03/Rfc/";
	String ITEM_NAMESPACE_URI = "http://Microsoft.LobServices.Sap/2007/03/Rfc/";
	String HEAD_NAMESPACE_URI = "http://Microsoft.LobServices.Sap/2007/03/Types/Rfc/";
	String ROOT_TAG_NAME = "Z_SMF01S_CREATE_FI_DOCUMENT";
	String HEAD_TAG_NAME = "I_HEAD";
	String ITEM_TAG_NAME = "T_ITEM";
	String ITEM_CONTENT_TAG = "ZSMFS021FS";
	String ITEM_CONTENT_NAMESPACE_URI = "http://Microsoft.LobServices.Sap/2007/03/Types/Rfc/";

	String ITEM_SEQ_NO_TAG = "SEQNO";

	ImmutableList<String> HEADER_FIELDS = ImmutableList.copyOf("SYSID,BUKRS,DOCNO,BLDAT,BLART,BUDAT,MONAT,WAERS,KURSF,WWERT,XBLNR,BKTXT,PARGB,WMWST,MWSTS,XMWST" .split(","));

	ImmutableList<String> LINE_FIELDS = ImmutableList.copyOf("ACTYP,NEWKO,NEWUM,DCIND,WRBTR,DMBTR,MWSKZ,GSBER,BUPLA,ZFBDT,ZTERM,ZBD1T,ZBD1P,ZBD2T,ZBD2P,ZBD3T,ZBFIX,WSKTO,SKFBT,REBZG,REBZJ,REBZZ,ZLSPR,ZLSCH,UZAWE,KDAUF,KDPOS,KDEIN,MATNR,WERKS,EBELN,EBELP,ZUONR,SGTXT,KKBER,BVTYP,XREF1,XREF2,RSTGR,XREF3,LZBKZ,ZSMFITMFGX" .split(","));
}
