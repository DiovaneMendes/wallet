import React from 'react'
import {Container,Col, Row, Button, Form, FormGroup, Label, Input,Card} from 'reactstrap';
import '../css/formUsuario.css'

 class FormUsuario extends React.Component {

    render(){ 
        return (
                <div>
                    <Container style={{ maxWidth: '600px', color:'gray' }}>
                        <Card style={{ padding: '10px 70px', border: '30px', marginTop : '5px', borderRadius:'30px'}}>
                            <Form>
                                <Row form>
                                <Col md={6}>
                                    <FormGroup>
                                    <Label for="name">nome Completo</Label>
                                    <Input type="name" name="name" id="name" placeholder="Insira seu nome" />
                                    </FormGroup>
                                </Col>
                                <Col md={6}>
                                    <FormGroup>
                                    <Label for="username">usuario</Label>
                                    <Input type="username" name="username" id="username" placeholder="nome de usuario" />
                                    </FormGroup>
                                </Col>
                                </Row>
                                <FormGroup>
                                    <Label for="email">email</Label>
                                    <Input type="email" name="email" id="email" placeholder="email@email.com" />
                                    </FormGroup>
                                <Row form>
                                <Col md={6}>
                                    <FormGroup>
                                    <Label for="password">senha</Label>
                                    <Input type="password" name="password" id="password"/>
                                    </FormGroup>
                                </Col>
                                <Col md={4}>
                                <FormGroup>
                                    <Label for="password">Repita a Senha</Label>
                                    <Input type="password" name="password" id="password"/>
                                    </FormGroup>
                                </Col>
                                </Row>
                                <Row>
                                <Col md={2}>
                                <FormGroup check>
                                    <Label check>
                                    <Input type="radio" name="radio1" />{' '}
                                    Administrador
                                    </Label>
                                    <Label check>
                                    <Input type="radio" name="radio1" />{' '}
                                    Gerente
                                    </Label>
                                </FormGroup> 
                                </Col>
                                </Row>
                                <div className='buttom'>
                                <Button color="success" type="submit">Salvar Usuario</Button>{' '}
                                </div>
                            </Form>
                        </Card>
                    </Container>
                </div>
        )
    }

}
export default FormUsuario