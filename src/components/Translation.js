import { Component } from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import deepForceUpdate from 'react-deep-force-update';
import { connect } from 'react-redux';

import { getMessages } from '../actions/AppActions';

class Translation extends Component {
  constructor(props) {
    super(props);
  }

  _getMessages = () => {
    getMessages().then(response => {
      if (window.Cypress) {
        window.Cypress.emit('emit:counterpartTranslations', response.data);
      }

      counterpart.registerTranslations('lang', response.data);
      counterpart.setLocale('lang');
      counterpart.setMissingEntryGenerator(function(key) {
        // eslint-disable-next-line no-console
        console.error(`Missing translation: ${key}`);
        return `{${key}}`;
      });

      deepForceUpdate(this);
    });
  };

  componentWillMount = () => {
    this._getMessages();
  };

  componentWillReceiveProps(nextProps) {
    if (nextProps.language !== this.props.language) {
      this._getMessages();
    }
  }

  render = () => this.props.children;
}

Translation.propTypes = {
  children: PropTypes.any,
  language: PropTypes.string,
};

const mapStateToProps = ({ appHandler: { me } }) => {
  const language = me.language ? me.language.key : 'de_DE';

  return {
    language: language,
  };
};

export default connect(mapStateToProps)(Translation);
