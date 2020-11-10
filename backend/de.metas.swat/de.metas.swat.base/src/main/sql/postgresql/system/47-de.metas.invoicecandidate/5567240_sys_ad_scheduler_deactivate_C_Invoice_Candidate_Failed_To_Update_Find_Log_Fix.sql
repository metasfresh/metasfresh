
UPDATE ad_scheduler set Isactive='N' where ad_scheduler_id=550036;
update ad_process
set description='Identifies, logs and reenqueues invoice candidates that need to be updated
! DOES *NOT* CREATE AN ASYNC_WORKPACKAGE TO PERFORM THE RE-EVALUATION !'
where ad_process_id=540679;
