public class Woman extends Person {
    private String originalLastName;

    public Woman(String firstName, String lastName, int age) {
        super(firstName, lastName, age);
        this.originalLastName = lastName; // Save original last name
    }

    public String getOriginalLastName() {
        return originalLastName;
    }

    @Override
    public boolean isRetired() {
        return age > 60;
    }

    @Override
    public void registerPartnership(Person partner) {
        super.registerPartnership(partner);
        if (partner instanceof Man) {
            this.setLastName(partner.getLastName()); // Woman takes the last name of the man
        }
    }

    @Override
    public void deregisterPartnership(boolean revertLastName) {
        if (revertLastName) {
            this.setLastName(this.originalLastName); // Revert to original last name
        }
        super.deregisterPartnership(revertLastName);
    }
}
