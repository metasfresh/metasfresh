--
-- OrderLineShipmentScheduleHandler
-- 
-- only rename the old one if the new one doesn't already somehow exist
UPDATE M_IolCandHandler 
SET ClassName='de.metas.order.inoutcandidate.OrderLineShipmentScheduleHandler' 
WHERE ClassName='de.metas.inoutcandidate.spi.impl.OrderLineInOutCandHandler'
	AND NOT EXists (select 1 from M_IolCandHandler WHERE ClassName='de.metas.order.inoutcandidate.OrderLineShipmentScheduleHandler');

-- deactivate the old one if it still exists
UPDATE M_IolCandHandler SET IsActive='N' WHERE ClassName='de.metas.inoutcandidate.spi.impl.OrderLineInOutCandHandler';
