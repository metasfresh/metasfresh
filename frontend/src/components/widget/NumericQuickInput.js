import NumericInput from 'react-numeric-input';

/**
 * Extended NumericInput component to handle numbers formatting
 * the way we wanted.
 * @private
 */
export default class NumericQuickInput extends NumericInput {
  _format(n) {
    let _n = this._toNumber(n);

    _n += '';

    if (this.props.format) {
      return this.props.format(_n);
    }

    return _n;
  }

  _parse(x) {
    x = String(x).replace(',', '.');
    if (typeof this.props.parse == 'function') {
      return parseFloat(this.props.parse(x));
    }
    return parseFloat(x);
  }
}
