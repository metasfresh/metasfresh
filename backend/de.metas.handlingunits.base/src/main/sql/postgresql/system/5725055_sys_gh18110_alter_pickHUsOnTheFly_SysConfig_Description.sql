update ad_sysconfig
set description =
                        'If set to N, there will be no picking on the fly, If set to Y or A, and the shipment run is supposed to create shipment lines even for quantities' ||
                        ' that were not picked, then the system will still allocate existing available HUs on the fly' ||
                        ' (with a mention, that in case of Y it will only pick source HUs, but in case of A it will pick any available HU and also create virtual ones for the missing qty).' ||
                        ' This can include both allocating complete HUs and splitting HUs to allocate matching storage quantities.' ||
                        ' Hardcoded default in case this sysconfig record is missing: Y!'
where name = 'de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PickAvailableHUsOnTheFly'
;
