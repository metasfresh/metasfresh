/*
 * #%L
 * metasfresh
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

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
