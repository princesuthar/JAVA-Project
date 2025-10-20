-- Update doctor information to Dr. Ishan Mota
UPDATE doctors 
SET 
    name = 'Dr. Ishan Mota',
    specialization = 'Conservative Dentistry and Endodontics',
    qualification = 'BDS, MDS - Conservative Dentistry and Endodontics',
    email = 'info@identistindia.com',
    phone = '+91 95522 63314',
    address = 'India'
WHERE id = 1;

-- Verify the update
SELECT * FROM doctors WHERE id = 1;
