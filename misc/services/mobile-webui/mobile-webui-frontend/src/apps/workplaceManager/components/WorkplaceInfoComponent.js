import { trl } from '../../../utils/translations';
import React from 'react';
import PropTypes from 'prop-types';
import { appTrl } from '../utils';

export const WorkplaceInfoComponent = ({ workplaceInfo }) => {
  return (
    <table className="table view-header is-size-6">
      <tbody>
        <tr>
          <th>{appTrl('workplaceName')}</th>
          <td>{workplaceInfo.name}</td>
        </tr>
        <tr>
          <th>{appTrl('warehouseName')}</th>
          <td>{workplaceInfo.warehouseName}</td>
        </tr>
        <tr>
          <th>{appTrl('isUserAssigned')}</th>
          <td>{trl(workplaceInfo.userAssigned ? 'general.Yes' : 'general.No')}</td>
        </tr>
      </tbody>
    </table>
  );
};

WorkplaceInfoComponent.propTypes = {
  workplaceInfo: PropTypes.object.isRequired,
};

export default WorkplaceInfoComponent;
