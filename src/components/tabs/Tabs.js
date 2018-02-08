import PropTypes from "prop-types";
import React, { Component } from "react";
import { connect } from "react-redux";

import { activateTab, unselectTab } from "../../actions/WindowActions";
import Tab from "./Tab";

class Tabs extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selected: props.children[0].key
    };
  }

  componentDidMount = () => {
    this.props.dispatch(activateTab("master", this.state.selected));
  };

  componentDidUpdate = (prevProps, prevState) => {
    const { dispatch } = this.props;
    if (prevState.selected !== this.state.selected) {
      dispatch(activateTab("master", this.state.selected));
    }
  };

  componentWillUnmount() {
    this.props.dispatch(unselectTab("master"));
  }

  handleClick = (e, id) => {
    e.preventDefault();
    this.setState({
      selected: id
    });
  };

  handlePillKeyDown = (e, key) => {
    if (e.key === "Enter") {
      this.handleClick(e, key);
    }
  };

  renderPills = pills => {
    const { tabIndex } = this.props;
    const maxWidth = 95 / pills.length + "%";
    const { selected } = this.state;
    return pills.map(item => {
      return (
        <li
          className="nav-item"
          key={"tab" + item.key}
          onClick={e => this.handleClick(e, item.key)}
          tabIndex={tabIndex}
          onKeyDown={e => this.handlePillKeyDown(e, item.key)}
          style={{ maxWidth }}
          title={item.props.caption}
        >
          <a className={"nav-link " + (selected === item.key ? "active" : "")}>
            {item.props.caption}
          </a>
        </li>
      );
    });
  };

  renderTabs = tabs => {
    const { toggleTableFullScreen, fullScreen, windowType } = this.props;
    const { selected } = this.state;

    return tabs.map(item => {
      const itemWithProps = Object.assign({}, item, {
        props: Object.assign({}, item.props, {
          toggleFullScreen: toggleTableFullScreen,
          fullScreen: fullScreen
        })
      });

      if (selected == item.key) {
        const { tabid, queryOnActivate, docId, orderBy } = item.props;

        return (
          <div key={"pane" + item.key} className="tab-pane active">
            <Tab
              {...{
                queryOnActivate,
                tabid,
                docId,
                windowType,
                orderBy
              }}
            >
              {itemWithProps}
            </Tab>
          </div>
        );
      } else {
        return false;
      }
    });
  };

  render() {
    const { children, tabIndex, fullScreen } = this.props;

    return (
      <div
        className={
          "mb-1 " + (fullScreen ? "tabs-fullscreen container-fluid " : "")
        }
      >
        <ul className="nav nav-tabs mt-1">{this.renderPills(children)}</ul>
        <div
          className="tab-content"
          tabIndex={tabIndex}
          ref={c => (this.tabContent = c)}
        >
          {this.renderTabs(children)}
        </div>
      </div>
    );
  }
}

Tabs.propTypes = {
  dispatch: PropTypes.func.isRequired
};

export default connect()(Tabs);
