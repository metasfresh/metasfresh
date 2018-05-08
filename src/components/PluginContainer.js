import React, { Component } from 'react';
import { connect } from 'react-redux';
import Container from '../components/Container';

class PluginContainer extends Component {
  render() {
    const { rawModal, modal, pluginName, component } = this.props;

    const TagName = component;

    return (
      <Container {...{ modal, rawModal }}>
        <div
          className={`plugin-host plugin-host-${pluginName}`}
          ref={c => (this.container = c)}
        >
          <TagName />
        </div>
      </Container>
    );
  }
}

function mapStateToProps(state) {
  const { windowHandler } = state;

  const { modal, rawModal } = windowHandler || {
    modal: {},
    rawModal: {},
  };

  return {
    modal,
    rawModal,
  };
}

export default connect(mapStateToProps)(PluginContainer);
