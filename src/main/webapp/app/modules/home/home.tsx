import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <div className="hero">
      <div className="video">
        <video autoPlay loop muted>
          <source src="content/images/coffee.mp4" type="video/mp4" />
        </video>
      </div>
      <Row>
        <Col md="9">
          <h2>Welcome to Sipp</h2>
          <p className="lead">Here are the most recent Sipps</p>
          {account?.login ? (
            <div>
              <Alert color="success">You are logged in as user &quot;{account.login}&quot;.</Alert>
            </div>
          ) : (
            <div>
              <Alert color="warning">
                If you want to
                <span>&nbsp;</span>
                <Link to="/login" className="alert-link">
                  sign in
                </Link>
                , you can try the default accounts:
                <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;) <br />- User (login=&quot;user&quot; and
                password=&quot;user&quot;).
              </Alert>

              <Alert color="warning">
                You don&apos;t have an account yet?&nbsp;
                <Link to="/account/register" className="alert-link">
                  Register a new account
                </Link>
              </Alert>
            </div>
          )}
          <p>If you would like to see more Brew Cards:</p>

          <ul>
            <li>
              {/*              <Link to="/brew-card" target="_blank" rel="noopener noreferrer"> */}
              <Link to="/brew-card" rel="noopener noreferrer">
                All Brew Cards
              </Link>
            </li>
            <li>
              {/*               <a href="https://stackoverflow.com/tags/jhipster/info" target="_blank" rel="noopener noreferrer"> */}
              <Link to="/brew-card-user" rel="noopener noreferrer">
                Your Brew Cards
              </Link>
            </li>
            <li>
              <a href="https://github.com/jhipster/generator-jhipster/issues?state=open" target="_blank" rel="noopener noreferrer">
                Most Recent Brew Cards
              </a>
            </li>
            <li>
              <a href="https://gitter.im/jhipster/generator-jhipster" target="_blank" rel="noopener noreferrer">
                Your Friends Brew Cards
              </a>
            </li>
            <li>
              <a href="https://twitter.com/jhipster" target="_blank" rel="noopener noreferrer">
                follow @Sipp on Twitter!
              </a>
            </li>
          </ul>
        </Col>
      </Row>
    </div>
  );
};

export default Home;
