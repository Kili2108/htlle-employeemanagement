package at.kili2108.employeemanagement;

class UnauthorizedException extends Exception {

    public UnauthorizedException(String message) {
        super(message);
    }
}
