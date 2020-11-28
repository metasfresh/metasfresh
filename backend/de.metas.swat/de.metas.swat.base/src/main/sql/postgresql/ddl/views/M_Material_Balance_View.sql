
CREATE OR REPLACE VIEW M_Material_Balance_View AS

SELECT
	bal.DocumentNo :: character varying(30),
	bal.PrintName,
	bal.MovementDate,
	bal.Name, --Product
	SUM(bal.Outgoing) AS Outgoing,
	SUM(bal.Incoming) AS Incoming,
	CarryIncoming,
	CarryOutgoing,
	bal.C_BPartner_ID, 
	bal.IsInvoiced

	FROM
		report.C_BPartner_InOutline_v bal

		LEFT OUTER JOIN M_Product p ON bal.M_Product_ID = p.M_Product_ID
		LEFT OUTER JOIN M_InOut io ON bal.M_InOut_ID = io.M_InOut_ID
		LEFT OUTER JOIN C_DocType dt ON io.C_DocType_ID = dt.C_DocType_ID

		LEFT OUTER JOIN (
			SELECT	SUM( Incoming ) AS CarryIncoming,
				SUM( Outgoing ) AS CarryOutgoing,
				C_BPartner_ID,  M_Product_ID, movementdate
			FROM	report.C_BPartner_InOutline_v
			WHERE	NOT IsInvoiced 
			GROUP BY C_BPartner_ID, M_Product_ID, movementDate
		) carry ON bal.C_BPartner_ID = carry.C_BPartner_ID AND bal.M_Product_ID = carry.M_Product_ID
	WHERE 
	
		NOT IsInvoiced
	
		/**
		 * Count only packing material
		 * There's a flag for this but accessing it here is slows down the query immensely
		 */
		AND p.M_Product_Category_ID = ((SELECT value::numeric FROM AD_SysConfig WHERE name = 'PackingMaterialProductCategoryID'))

AND
	MovementQty IS NOT NULL OR CarryIncoming != 0 OR CarryOutgoing != 0
GROUP BY
	bal.C_BPartner_ID,
	bal.M_Product_ID,
	bal.MovementDate,
	bal.DocumentNo,
	bal.PrintName,
	bal.Name, --Product
	CarryIncoming,
	CarryOutgoing,
	IsInvoiced
ORDER BY
	bal.C_BPartner_ID, bal.name, bal.movementdate, bal.documentno
;




