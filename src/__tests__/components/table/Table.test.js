import React from 'react';
import { List } from 'immutable';
import { mount, shallow, render } from 'enzyme';
import uuid from 'uuid/v4';

import Table, { Table as TableBare } from '../../../../components/table/Table';
import fixtures from '../../../../../test_setup/fixtures/table_full.json';

const TAB = fixtures.tabs[0];
const DATA = fixtures.data;

describe('Table in Tab component', () => {
  describe('rendering tests:', () => {
    it('renders without errors', () => {

    });

    it('cells widgets are rendered when necessary', () => {
      
    });
  });

  describe('Rows navigation tests:', () => {
    it('Cell is selected and row focused', () => {
    });

    it('Lookup widget is focused on selecting row', () => {
    });

    it('Lookup widget is blurred on patch and re-focused on key', () => {
    });

    it('Lookup widget is blurred on keys', () => {
    });

    it('Lookup widget is re-focused on select after traversing back', () => {
    });

    it('Number widget is focused on selecting row', () => {
    });

    it('Number widget is blurred on patch and re-focused on key', () => {
    });

    it('Number widget is blurred on keys', () => {
    });

    it('Number widget is re-focused on select after traversing back', () => {
    });

    it('Text widget is focused on selecting row', () => {
    });

    it('Text widget is blurred on patch and re-focused on key', () => {
    });

    it('Text widget is blurred on keys', () => {
    });

    it('Text widget is re-focused on select after traversing back', () => {
    });

    it('Textarea widget is focused on selecting row', () => {
    });

    it('Textarea widget is blurred on patch and re-focused on key', () => {
    });

    it('Textarea widget is blurred on keys', () => {
    });

    it('Textarea widget is re-focused on select after traversing back', () => {
    });
  });
});