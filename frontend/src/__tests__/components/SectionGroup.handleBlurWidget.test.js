import React from 'react';
import ReactDOM from 'react-dom';

import SectionGroup from '../../components/SectionGroup';

/**
 * handleBlurWidget moves the cursor to the next widget once a widget's value is saved. The save can finish
 * after the user put the cursor somewhere else (Alt+Q opened the quick entry and put it in the product field):
 * the cursor must then stay where the user put it.
 */
class FakeWidget extends React.Component {
  render() {
    return (
      <div>
        <input tabIndex={0} data-field={this.props.widgetData[0].field} />
      </div>
    );
  }
}

describe('SectionGroup.handleBlurWidget', () => {
  let sectionGroup, partnerInput, billPartnerInput, elsewhere;

  beforeEach(() => {
    document.body.innerHTML = '';
    const render = (field) => {
      const container = document.createElement('div');
      document.body.append(container);
      // eslint-disable-next-line react/no-render-return-value
      return ReactDOM.render(<FakeWidget widgetData={[{ field }]} />, container);
    };
    sectionGroup = new SectionGroup({ layout: {} });
    sectionGroup.widgets = [render('C_BPartner_ID'), render('Bill_BPartner_ID')];
    partnerInput = document.querySelector('[data-field="C_BPartner_ID"]');
    billPartnerInput = document.querySelector('[data-field="Bill_BPartner_ID"]');
    elsewhere = document.createElement('input');
    document.body.append(elsewhere);
  });

  it('moves the cursor to the next widget when it is still in the widget that was left', () => {
    partnerInput.focus();

    sectionGroup.handleBlurWidget('C_BPartner_ID');

    expect(document.activeElement).toBe(billPartnerInput);
  });

  it('moves the cursor to the next widget when no element holds it', () => {
    document.activeElement.blur();

    sectionGroup.handleBlurWidget('C_BPartner_ID');

    expect(document.activeElement).toBe(billPartnerInput);
  });

  it('leaves the cursor where it is when it left the widget before the value was saved', () => {
    elsewhere.focus();

    sectionGroup.handleBlurWidget('C_BPartner_ID');

    expect(document.activeElement).toBe(elsewhere);
  });
});
