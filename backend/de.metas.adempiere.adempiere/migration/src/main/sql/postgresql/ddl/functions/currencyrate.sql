-- Function: currencyrate(numeric, numeric, timestamp with time zone, numeric, numeric, numeric)

-- DROP FUNCTION currencyrate(numeric, numeric, timestamp with time zone, numeric, numeric, numeric);

DROP FUNCTION IF EXISTS currencyrate(p_curfrom_id numeric, p_curto_id numeric, p_convdate timestamp with time zone, p_conversiontype_id numeric, p_client_id numeric, p_org_id numeric);

CREATE OR REPLACE FUNCTION currencyrate(p_curfrom_id numeric, p_curto_id numeric, p_convdate timestamp with time zone, p_conversiontype_id numeric, p_client_id numeric, p_org_id numeric)
  RETURNS numeric AS
$BODY$

/*************************************************************************
 * The contents of this file are subject to the Compiere License.  You may
 * obtain a copy of the License at    http://www.compiere.org/license.html
 * Software is on an  "AS IS" basis,  WITHOUT WARRANTY OF ANY KIND, either
 * express or implied. See the License for details. Code: Compiere ERP+CRM
 * Copyright (C) 1999-2001 Jorg Janke, ComPiere, Inc. All Rights Reserved.
 *
 * converted to postgreSQL by Karsten Thiemann (Schaeffer AG),
 * kthiemann@adempiere.org
 *************************************************************************
 ***
 * Title:	Return Conversion Rate
 * Description:
 *		from CurrencyFrom_ID to CurrencyTo_ID
 *		Returns NULL, if rate not found
 * Test
 *		SELECT currencyrate(116, 100, null, null, null, null) FROM AD_System;  => .647169
 ************************************************************************/


DECLARE
	--	Currency From variables
	cf_IsEuro		CHAR(1);
	cf_IsEMUMember		CHAR(1);
	cf_EMUEntryDate		timestamp with time zone;
	cf_EMURate		NUMERIC;
	--	Currency To variables
	ct_IsEuro		CHAR(1);
	ct_IsEMUMember		CHAR(1);
	ct_EMUEntryDate	DATE;
	ct_EMURate		NUMERIC;
	--	Triangle
	v_CurrencyFrom		NUMERIC;
	v_CurrencyTo		NUMERIC;
	v_CurrencyEuro		NUMERIC;
	--
	v_ConvDate		timestamp with time zone := trunc(now(), 'DD');
	v_ConversionType_ID	NUMERIC := 0;
	v_Rate			NUMERIC;
	c			RECORD;

BEGIN
	-- NOTE to developer: keep in sync with org.adempiere.service.impl.CurrencyDAO.retrieveDefaultConversionType(Properties, int, int, Date)

--	No Conversion
	IF (p_CurFrom_ID = p_CurTo_ID) THEN
		RETURN 1;
	END IF;
	--	Default Date Parameter
	IF (p_ConvDate IS NOT NULL) THEN
		v_ConvDate := p_ConvDate;   --  SysDate
	END IF;

	--
    --  Default Conversion Type
	IF (p_ConversionType_ID IS NULL OR p_ConversionType_ID <= 0)
	THEN
		BEGIN
			v_ConversionType_ID := getDefaultConversionType_ID(p_Client_ID, p_Org_ID, p_ConvDate);
			if (v_ConversionType_ID is null) then
				RAISE NOTICE 'Conversion Type Not Found';
			end if;
		EXCEPTION WHEN OTHERS THEN
		    RAISE NOTICE 'Conversion Type Not Found';
		END;
    ELSE
        v_ConversionType_ID := p_ConversionType_ID;
	END IF;

	--	Get Currency Info
	SELECT	MAX(IsEuro), MAX(IsEMUMember), MAX(EMUEntryDate), MAX(EMURate)
	  INTO	cf_IsEuro, cf_IsEMUMember, cf_EMUEntryDate, cf_EMURate
	FROM		C_Currency
	  WHERE	C_Currency_ID = p_CurFrom_ID;
	-- Not Found
	IF (cf_IsEuro IS NULL) THEN
		RAISE NOTICE 'From Currency Not Found';
		RETURN NULL;
	END IF;
	SELECT	MAX(IsEuro), MAX(IsEMUMember), MAX(EMUEntryDate), MAX(EMURate)
	  INTO	ct_IsEuro, ct_IsEMUMember, ct_EMUEntryDate, ct_EMURate
	FROM		C_Currency
	  WHERE	C_Currency_ID = p_CurTo_ID;
	-- Not Found
	IF (ct_IsEuro IS NULL) THEN
		RAISE NOTICE 'To Currency Not Found';
		RETURN NULL;
	END IF;

	--	Fixed - From Euro to EMU
	IF (cf_IsEuro = 'Y' AND ct_IsEMUMember ='Y' AND v_ConvDate >= ct_EMUEntryDate) THEN
		RETURN ct_EMURate;
	END IF;

	--	Fixed - From EMU to Euro
	IF (ct_IsEuro = 'Y' AND cf_IsEMUMember ='Y' AND v_ConvDate >= cf_EMUEntryDate) THEN
		RETURN 1 / cf_EMURate;
	END IF;

	--	Fixed - From EMU to EMU
	IF (cf_IsEMUMember = 'Y' AND cf_IsEMUMember ='Y'
			AND v_ConvDate >= cf_EMUEntryDate AND v_ConvDate >= ct_EMUEntryDate) THEN
		RETURN ct_EMURate / cf_EMURate;
	END IF;

	--	Flexible Rates
	v_CurrencyFrom := p_CurFrom_ID;
	v_CurrencyTo := p_CurTo_ID;

	-- if EMU Member involved, replace From/To Currency
	IF ((cf_isEMUMember = 'Y' AND v_ConvDate >= cf_EMUEntryDate)
	  OR (ct_isEMUMember = 'Y' AND v_ConvDate >= ct_EMUEntryDate)) THEN
		SELECT	MAX(C_Currency_ID)
		  INTO	v_CurrencyEuro
		FROM		C_Currency
		WHERE	IsEuro = 'Y';
		-- Conversion Rate not Found
		IF (v_CurrencyEuro IS NULL) THEN
			RAISE NOTICE 'Euro Not Found';
			RETURN NULL;
		END IF;
		IF (cf_isEMUMember = 'Y' AND v_ConvDate >= cf_EMUEntryDate) THEN
			v_CurrencyFrom := v_CurrencyEuro;
		ELSE
			v_CurrencyTo := v_CurrencyEuro;
		END IF;
	END IF;

	--	Get Rate

	BEGIN
		FOR c IN SELECT	MultiplyRate
			FROM	C_Conversion_Rate
			WHERE	C_Currency_ID=v_CurrencyFrom AND C_Currency_ID_To=v_CurrencyTo
              AND   IsActive='Y'
			  AND	C_ConversionType_ID=v_ConversionType_ID
			  AND	v_ConvDate BETWEEN ValidFrom AND ValidTo
			  AND	AD_Client_ID IN (0,p_Client_ID) AND AD_Org_ID IN (0,p_Org_ID)
			ORDER BY AD_Client_ID DESC, AD_Org_ID DESC, ValidFrom DESC
		LOOP
			v_Rate := c.MultiplyRate;
			EXIT;	--	only first
		END LOOP;
	END;
	--	Not found
	IF (v_Rate IS NULL) THEN
		RAISE NOTICE 'Conversion Rate Not Found for C_Currency=%->%, C_ConversionType_ID=%, ConvDate=%, Client/Org=%/%', v_CurrencyFrom, v_CurrencyTo, v_ConversionType_ID, v_ConvDate, p_Client_ID, p_Org_ID;
		RETURN NULL;
	END IF;

	--	Currency From was EMU
	IF (cf_isEMUMember = 'Y' AND v_ConvDate >= cf_EMUEntryDate) THEN
		RETURN v_Rate / cf_EMURate;
	END IF;

	--	Currency To was EMU
	IF (ct_isEMUMember = 'Y' AND v_ConvDate >= ct_EMUEntryDate) THEN
		RETURN v_Rate * ct_EMURate;
	END IF;

	RETURN v_Rate;

EXCEPTION WHEN OTHERS THEN
	RAISE NOTICE '%', SQLERRM;
	RETURN NULL;


END;

$BODY$
LANGUAGE plpgsql STABLE
COST 100;
