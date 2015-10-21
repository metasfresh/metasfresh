update AD_Field set EntityType='de.metas.esb.edi'
where AD_Field_ID in (
	-- M_InOut.IsEdiEnabled (shipment, receipt windows)
	553213, 553216
);

