-- Function: currencyround(numeric, numeric, character varying)

-- DROP FUNCTION currencyround(numeric, numeric, character varying);

CREATE OR REPLACE FUNCTION currencyround(p_amount numeric, p_curto_id numeric, p_costing character varying)
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
 * Title:	Round amount for Traget Currency
 * Description:
 *		Round Amount using Costing or Standard Precision
 *		Returns unmodified amount if currency not found
 * Test:
 *		SELECT currencyRound(currencyConvert(100,116,100,null,null),100,null) FROM AD_System => 64.72 
 ************************************************************************/
	
	
DECLARE
	v_StdPrecision		int;
	v_CostPrecision		int;

BEGIN
	--	Nothing to convert
	IF (p_Amount IS NULL OR p_CurTo_ID IS NULL) THEN
		RETURN p_Amount;
	END IF;

	--	Ger Precision
	SELECT	MAX(StdPrecision), MAX(CostingPrecision)
	  INTO	v_StdPrecision, v_CostPrecision
	FROM	C_Currency
	  WHERE	C_Currency_ID = p_CurTo_ID;
	--	Currency Not Found
	IF (v_StdPrecision IS NULL) THEN
		RETURN p_Amount;
	END IF;

	IF (p_Costing = 'Y') THEN
		RETURN ROUND (p_Amount, v_CostPrecision);
	END IF;

	RETURN ROUND (p_Amount, v_StdPrecision);
	
END;

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;

