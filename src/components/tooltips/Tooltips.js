import React, { Component } from "react";

class Tooltips extends Component {
  constructor(props) {
    super(props);

    this.state = {
      opacity: 0
    };
  }

  componentDidMount() {
    const { delay } = this.props;
    this.timeout = setTimeout(() => {
      this.setState({
        opacity: 1
      });
    }, delay ? delay : 1000);
  }

  componentWillUnmount() {
    clearTimeout(this.timeout);
  }

  render() {
    const {
      name,
      action,
      type,
      extraClass,
      tooltipOnFirstlevelPositionLeft
    } = this.props;
    const { opacity } = this.state;
    return (
      <div style={{ opacity: opacity }}>
        <div
          className={
            "tooltip-wrapp " +
            (type ? "tooltip-" + type : "") +
            " " +
            (extraClass ? extraClass : "")
          }
          style={{ left: tooltipOnFirstlevelPositionLeft + "px" }}
        >
          <div className="tooltip-shortcut">{name}</div>
          <div className="tooltip-name">{action}</div>
        </div>
      </div>
    );
  }
}

export default Tooltips;
