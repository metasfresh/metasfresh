import * as React from 'react';
import { mount } from '@cypress/react';
import Tooltips from '../../frontend/src/components/tooltips/Tooltips';
it('Tooltips renders', () => {
  mount(<Tooltips className="filter-tooltip" name="TEST" action="Apply" type={''} />);
  // cy.get('tooltip-wrapp').contains('TEST').click();
});
