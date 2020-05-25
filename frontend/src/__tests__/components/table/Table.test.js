import React from 'react';
import { shallow, render } from 'enzyme';
import renderer from 'react-test-renderer';
import tableProps from '../../../../test_setup/fixtures/table/table.json';
import Table from '../../../components/table/Table';


describe('Table row (TableItem)', () => {
  it('renders without errors', () => {
    shallow(<Table {...tableProps} />);
  });
});
