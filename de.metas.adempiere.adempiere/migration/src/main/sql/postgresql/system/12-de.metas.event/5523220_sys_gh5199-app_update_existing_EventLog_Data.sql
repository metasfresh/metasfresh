
UPDATE AD_EventLog 
SET 
	EventData=replace(eventData, '"storeEvent" : true', '"loggingStatus" : "WAS_LOGGED"'),
	Updated=now(),
	UpdatedBy=99
WHERE EventData ilike '%"storeEvent" : true%'
;
