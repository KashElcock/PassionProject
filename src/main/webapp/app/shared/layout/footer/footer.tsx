import './footer.scss';

import React from 'react';

import { Col, Row } from 'reactstrap';

const Footer = () => (
  <div className="footer page-content">
    <Row>
      <Col md="12">
        <p>
          Created by <a href="https://www.linkedin.com/in/kashelcock">Kash Elcock</a>
        </p>
      </Col>
    </Row>
  </div>
);

export default Footer;
