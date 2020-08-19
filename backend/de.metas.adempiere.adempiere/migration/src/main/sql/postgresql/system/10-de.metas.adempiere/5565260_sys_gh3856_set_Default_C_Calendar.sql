
select db_fail_unless_row_count_greater_zero('update C_Calendar set IsDefault=''Y'' where c_calendar_id=1000000;');
