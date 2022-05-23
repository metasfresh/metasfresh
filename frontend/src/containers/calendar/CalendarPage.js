import React from 'react';
import Calendar from './Calendar';
import Header from '../../components/header/Header';

const CalendarPage = () => {
  //
  return (
    <div>
      <Header />
      <div className="header-sticky-distance js-unselect panel-vertical-scroll dashboard">
        <Calendar />
      </div>
    </div>
  );
};

export default CalendarPage;
