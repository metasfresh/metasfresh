import { Hints } from 'intro.js-react';
import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import Container from '../components/Container';
import DraggableWrapper from '../components/dashboard/DraggableWrapper';

// import { introHints } from '../components/intro/intro';

const mapStateToProps = state => ({
  modal: state.windowHandler.modal,
  rawModal: state.windowHandler.rawModal,
  indicator: state.windowHandler.indicator,
  includedView: state.listHandler.includedView,
  enableTutorial: state.appHandler.enableTutorial,
  processStatus: state.appHandler.processStatus,
  me: state.appHandler.me,
});

export class Dashboard extends Component {
  state = {
    editmode: false,
    hintsEnabled: null,
    introHints: null,
  };

  static propTypes = {
    dispatch: PropTypes.func.isRequired,
  };

  componentDidUpdate() {
    // TODO: Resolve this hotfix
    return;

    /*
        const { me } = this.props;

        if (me) {
            let docIntroHints;

            if (Array.isArray(introHints['default'])) {
                docIntroHints = introHints['default'];
            }

            this.setState({
                hintsEnabled: (docIntroHints && (docIntroHints.length > 0)),
                introHints: docIntroHints,
            });
        }
        */
  }

  toggleEditMode = () => {
    this.setState(prevState => ({ editmode: !prevState.editmode }));
  };

  render() {
    const {
      location,
      modal,
      rawModal,
      indicator,
      processStatus,
      includedView,
      enableTutorial,
    } = this.props;
    const { editmode, hintsEnabled, introHints } = this.state;

    return (
      <Container
        siteName="Dashboard"
        noMargin
        onEditModeToggle={this.toggleEditMode}
        modal={modal}
        rawModal={rawModal}
        indicator={indicator}
        processStatus={processStatus}
        includedView={includedView}
        editmode={editmode}
      >
        <div className="container-fluid dashboard-wrapper">
          <DraggableWrapper
            editmode={editmode}
            toggleEditMode={this.toggleEditMode}
            dashboard={location.pathname}
          />
        </div>

        {enableTutorial &&
          introHints &&
          introHints.length > 0 && (
            <Hints enabled={hintsEnabled} hints={introHints} />
          )}
      </Container>
    );
  }
}

export default connect(mapStateToProps)(Dashboard);
