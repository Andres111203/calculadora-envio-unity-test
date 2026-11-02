package co.univalle.envio;

/**
 * Hello world!
 *
 */
public class CalculadoraTarifaEnvio
{

    public float calcularTotal(float montoCompra, TipoCliente tipoCliente, boolean esCuponValido, int distanciaKm){
        if(montoCompra < 0) {
        }

        return 0;
    }

    private void validarParams(float montoCompra, TipoCliente tipoCliente, int distanciaKm){
        if(montoCompra <= 0){
            throw new IllegalArgumentException("El monto de la compra deber ser mayor que 0");
        }
        if(distanciaKm < 1 || distanciaKm > 500){
            throw new IllegalArgumentException("La distancia debe estar entre 1 y 500 km");
        }
        if(tipoCliente == null){
            throw new IllegalArgumentException("El tipo de cliente no puede ser nulo o desconocido");
        }
    }

    private float aplicarDescuentoCliente(float montoCompra, TipoCliente tipoCliente) {
        return switch (tipoCliente) {
            case ESTANDAR -> montoCompra;
            case VIP -> montoCompra * 0.85f;
            case CORPORATIVO -> montoCompra >= 1000 ? montoCompra * 0.70f : montoCompra * 0.80f;
        };
    }

    private float aplicarDescuentoCupon(float monto, boolean esCuponValido) {
        return esCuponValido ? monto * 0.95f : monto;
    }

    private float calcularTarifaEnvio(float montoNeto, TipoCliente tipoCliente, float montoOriginal, int distanciaKm) {
        if (montoNeto >= 500 || (tipoCliente == TipoCliente.CORPORATIVO && montoOriginal >= 1000))
            return 0;
        if (distanciaKm <= 50)  return 5;
        if (distanciaKm <= 200) return 10;
        return 20;
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
