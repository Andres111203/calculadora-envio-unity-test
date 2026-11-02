package co.univalle.envio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



/**
 * Unit test for simple App.
 */
class CalculadoraTarifaEnvioTest {

    private CalculadoraTarifaEnvio calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalculadoraTarifaEnvio();
    }

    // Pruebas del camino feliz

    @Test
    void clienteEstandar_sinCupon_distanciaCorta_retornaTotalCorrecto() {
        float resultado = calculadora.calcularTotal(100, TipoCliente.ESTANDAR, false, 25);
        assertEquals(105f, resultado, 0.01f);
    }

    @Test
    void clienteVip_sinCupon_distanciaMedia_retornaTotalCorrecto() {
        float resultado = calculadora.calcularTotal(200, TipoCliente.VIP, false, 100);
        assertEquals(180f, resultado, 0.01f);
    }

    @Test
    void clienteVip_conCupon_distanciaCorta_retornaTotalCorrecto() {
        float resultado = calculadora.calcularTotal(200, TipoCliente.VIP, true, 25);
        assertEquals(166.5f, resultado, 0.01f);
    }

    @Test
    void clienteCorporativo_montoMenor1000_sinCupon_retornaTotalCorrecto() {
        float resultado = calculadora.calcularTotal(500, TipoCliente.CORPORATIVO, false, 25);
        assertEquals(405f, resultado, 0.01f);
    }

    @Test
    void clienteCorporativo_montoMayorIgual1000_sinCupon_retornaEnvioGratis() {
        float resultado = calculadora.calcularTotal(1000, TipoCliente.CORPORATIVO, false, 300);
        assertEquals(700f, resultado, 0.01f);
    }

    // Pruebas de valores límite

    @Test
    void montoCompra_valorMinimo_0punto01_esValido() {
        float resultado = calculadora.calcularTotal(0.01f, TipoCliente.ESTANDAR, false, 1);
        assertEquals(5.01f, resultado, 0.01f);
    }

    @Test
    void montoCompra_499punto99_noAplicaEnvioGratis() {
        float resultado = calculadora.calcularTotal(499.99f, TipoCliente.ESTANDAR, false, 25);
        assertEquals(504.99f, resultado, 0.01f);
    }

    @Test
    void montoCompra_500_aplicaEnvioGratis() {
        float resultado = calculadora.calcularTotal(500, TipoCliente.ESTANDAR, false, 25);
        assertEquals(500f, resultado, 0.01f);
    }

    @Test
    void montoCompra_999punto99_corporativo_aplicaDescuento20() {
        float resultado = calculadora.calcularTotal(999.99f, TipoCliente.CORPORATIVO, false, 25);
        assertEquals(799.99f, resultado, 0.01f);
    }

    @Test
    void montoCompra_1000_corporativo_aplicaDescuento30() {
        float resultado = calculadora.calcularTotal(1000, TipoCliente.CORPORATIVO, false, 25);
        assertEquals(700f, resultado, 0.01f);
    }

    @Test
    void distancia_limiteInferior_1km_tarifaCorta() {
        float resultado = calculadora.calcularTotal(100, TipoCliente.ESTANDAR, false, 1);
        assertEquals(105f, resultado, 0.01f);
    }

    @Test
    void distancia_50km_tarifaCorta() {
        float resultado = calculadora.calcularTotal(100, TipoCliente.ESTANDAR, false, 50);
        assertEquals(105f, resultado, 0.01f);
    }

    @Test
    void distancia_51km_tarifaMedia() {
        float resultado = calculadora.calcularTotal(100, TipoCliente.ESTANDAR, false, 51);
        assertEquals(110f, resultado, 0.01f);
    }

    @Test
    void distancia_200km_tarifaMedia() {
        float resultado = calculadora.calcularTotal(100, TipoCliente.ESTANDAR, false, 200);
        assertEquals(110f, resultado, 0.01f);
    }

    @Test
    void distancia_201km_tarifaLarga() {
        float resultado = calculadora.calcularTotal(100, TipoCliente.ESTANDAR, false, 201);
        assertEquals(120f, resultado, 0.01f);
    }

    @Test
    void distancia_500km_tarifaLarga() {
        float resultado = calculadora.calcularTotal(100, TipoCliente.ESTANDAR, false, 500);
        assertEquals(120f, resultado, 0.01f);
    }

    // Pruebas de excepciones

    @Test
    void montoCompra_cero_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                calculadora.calcularTotal(0, TipoCliente.ESTANDAR, false, 25));
    }

    @Test
    void montoCompra_negativo_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                calculadora.calcularTotal(-100, TipoCliente.ESTANDAR, false, 25));
    }

    @Test
    void distancia_cero_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                calculadora.calcularTotal(100, TipoCliente.ESTANDAR, false, 0));
    }

    @Test
    void distancia_mayor500_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                calculadora.calcularTotal(100, TipoCliente.ESTANDAR, false, 501));
    }

    @Test
    void tipoCliente_nulo_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                calculadora.calcularTotal(100, null, false, 25));
    }
}
