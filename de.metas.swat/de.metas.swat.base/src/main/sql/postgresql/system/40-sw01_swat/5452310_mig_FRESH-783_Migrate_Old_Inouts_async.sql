


CREATE OR REPLACE FUNCTION create_request_document_number ()
RETURNS character varying
AS
$BODY$


WITH updated AS (
	UPDATE AD_Sequence 
	SET CurrentNext = CurrentNext + 1
	WHERE AD_Sequence_ID = 545441 -- this is the ID of the document number sequence. 
	--I did not uset he name 'DocumentNo_R_Request' because there is another sequence with this name in the db and that one is incorrect
	RETURNING (CurrentNext - 1) :: character varying as documentNo) 
 select documentNo  FROM updated;

$BODY$
LANGUAGE sql volatile;




DROP TABLE IF EXISTS temp_M_InOutLine_To_R_Request; 

CREATE TABLE temp_M_InOutLine_To_R_Request
AS
(
	SELECT
		io.AD_Client_ID :: numeric(10,0) as AD_Client_ID,
		io.AD_Org_ID :: numeric(10,0) as AD_Org_ID,
		now() :: timestamp with time zone as Created,
		100 :: integer as CreatedBy,
		'Y' :: character(1) as IsActive,
		now() :: timestamp with time zone as Updated,
		100 :: integer as UpdatedBy ,
		
		nextval('r_request_seq') :: bigint as R_Request_ID,
		create_request_document_number() :: character varying as DocumentNo,
		
		io.M_InOut_ID :: numeric(10,0) as M_InOut_ID,
		iol.M_Product_ID :: numeric(10,0) as M_Product_ID,
		(select av.Value from M_AttributeValue av where av.M_Attribute_ID = (select M_Attribute_ID from M_Attribute where value = 'QualityNotice') and av.Name = iol.qualityNote)
		 :: character varying(255) as QualityNote,
		
		get_table_ID('M_InOut') :: numeric as AD_Table_ID,
		io.M_InOut_ID  :: numeric(10,0) as Record_ID,
		io.C_BPartner_ID :: numeric(10,0) as C_BPartner_ID,
		io.AD_User_ID :: numeric(10,0) as AD_User_ID,
		io.MovementDate :: timestamp with time zone as DateDelivered,
		
		(case 
			when io.IsSoTrx = 'Y' 
			then (select rt.R_RequestType_ID from R_RequestType rt where rt.InternalName = 'A_CustomerComplaint')
			else (select rt.R_RequestType_ID from R_RequestType rt where rt.InternalName = 'B_VendorComplaint') end 
		) :: numeric(10,0) as R_RequestType_ID,
		
		(select m.msgText from AD_Message m where m.value = 'R_Request_From_InOut_Summary') :: character varying(2000) as Summary,
		
		(
			select u. AD_User_ID 
			from AD_User u 
			join C_BPartner bp on  u.C_BPartner_ID = bp.C_BPartner_ID
			where u.IsDefaultContact = 'Y'
				and io.AD_Org_ID = bp.AD_OrgBP_ID 
				and u.IsActive = 'Y'
				and bp.IsActive = 'Y'
		) :: numeric(10,0) as SalesRep_ID,
		
		-- Mandatory fields
		'I':: character(1) as ConfidentialType, -- ConfidentialType Internal
		'I':: character(1) as ConfidentialTypeEntry, -- ConfidentialTypeEntry Internal
		'5':: character(1) as Priority, -- priority Medium
		'7' :: character (1) as DueType -- DueType Geplant
		
	FROM 
		M_InOutLine iol
		JOIN M_InOut io on iol.M_InOut_ID = io.M_InOut_ID
	WHERE iol.QualityDiscountPercent > 0
		and iol.IsActive = 'Y' 
		and io.IsActive = 'Y'

		and io.MovementDate >= '2015-01-01'

		and (not exists 
			(select 1 from R_Request r where r.Record_ID = io.M_InOut_ID and r.AD_Table_ID = get_table_ID('M_InOut')))

)
;


 


-- execute async
SELECT "de.metas.async".executesqlasync('
INSERT INTO R_Request
	(
		AD_Client_ID,
		AD_Org_ID,
		Created,
		CreatedBy,
		IsActive, 
		Updated,
		UpdatedBy,
		
		R_Request_ID,
		DocumentNo,
		
		
		M_InOut_ID,
		M_Product_ID,
		QualityNote,
		
		AD_Table_ID,
		Record_ID,
		C_BPartner_ID,
		AD_User_ID,
		DateDelivered,
		
		R_RequestType_ID,
		Summary,
		
		SalesRep_ID,
		ConfidentialType,
		ConfidentialTypeEntry,
		Priority,
		DueType
	)
 select 
	iolr.AD_Client_ID,
	iolr.AD_Org_ID,
	iolr.Created,
	iolr.CreatedBy,
	iolr.IsActive, 
	iolr.Updated,
	iolr.UpdatedBy,
	
	iolr.R_Request_ID,
	iolr.DocumentNo,
	
	iolr.M_InOut_ID,
	iolr.M_Product_ID,
	iolr.QualityNote,
	
	iolr.AD_Table_ID,
	iolr.Record_ID,
	iolr.C_BPartner_ID,
	iolr.AD_User_ID,
	iolr.DateDelivered,
	
	iolr.R_RequestType_ID,
	iolr.Summary,
	
	iolr.SalesRep_ID,
	iolr.ConfidentialType,
	iolr.ConfidentialTypeEntry,
	iolr.Priority,
	iolr.DueType
 from temp_M_InOutLine_To_R_Request iolr
 where not exists (select 1 from R_Request where R_Request_ID = iolr.R_Request_ID) ;');
  --;
  
-- Clean

--DROP TABLE IF EXISTS temp_M_InOutLine_To_R_Request;

