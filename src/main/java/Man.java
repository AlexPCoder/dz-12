public class Man extends Person {

    public Man(String firstName, String lastName, int age) {
        super(firstName, lastName, age);
    }

    @Override
    public boolean isRetired() {
        return age > 65;
    }

    @Override
    public void registerPartnership(Person partner) {
        super.registerPartnership(partner);
        if (partner instanceof Woman) {
            Woman womanPartner = (Woman) partner;
            womanPartner.setLastName(this.lastName); // Woman takes the last name of the man
        }
    }

    @Override
    public void deregisterPartnership(boolean revertLastName) {
        if (partner instanceof Woman) {
            Woman womanPartner = (Woman) partner;
            if (revertLastName) {
                womanPartner.setLastName(womanPartner.getOriginalLastName()); // Revert to original last name
            }
        }
        super.deregisterPartnership(revertLastName);
    }


}