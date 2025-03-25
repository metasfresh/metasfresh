#  #%L
#  de-metas-camel-edi
#  %%
#  Copyright (C) 2025 metas GmbH
#  %%
#  This program is free software: you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as
#  published by the Free Software Foundation, either version 2 of the
#  License, or (at your option) any later version.
# 
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
#  GNU General Public License for more details.
# 
#  You should have received a copy of the GNU General Public
#  License along with this program. If not, see
#  <http://www.gnu.org/licenses/gpl-2.0.html>.
#  #L%

import json  # Needed for saving the anonymization_map in a readable format
import os
import random
import re
import string
import xml.etree.ElementTree as ET

# Directory containing XML files
#xml_directory = "path/to/xml/files"  # Update this path
xml_directory=r"C:\work\new_dawn_uat\metasfresh\misc\services\camel\de-metas-camel-edi\src\test\resources\de\metas\edi\esb\invoicexport\edifact"

# Dictionary to maintain consistent pseudonomization
pseudonomization_map = {}

# Elements to exclude from pseudonomization
EXCLUDED_ELEMENTS = {
    "Line",
    "ISO_Code",
    "eancom_doctype",
    "eancom_locationtype",
    "CountryCode",
    "IsTaxExempt",
    "taxfree",
    "DeliveryRule",
    "DeliveryViaRule",
    "IsManualPrice",
    "LineNetAmt",
    "Discount",
    "ExternalSeqNo"
}

# Patterns to match element names to exclude from pseudonomization
EXCLUDED_ELEMENT_PATTERNS = [
    r".*Qty.*",  # Elements where name contains "Qty"
    r".*UOM.*",   # Elements where name contains "UON"
    r".PackagingCode.*",
    r".SeqNo.*"
]

# Function to determine if a value should be preserved (e.g., dates or times)
def is_date_or_time(value):
    # Regex to detect common date/time formats
    date_patterns = [
        r"^\d{4}-\d{2}-\d{2}([T\s]\d{2}:\d{2}(:\d{2})?)?([+-]\d{2}:\d{2})?$",  # YYYY-MM-DD+hh:mm or YYYY-MM-DDThh:mm:ss+hh:mm
        r"^\d{2}:\d{2}(:\d{2})?$",                                               # HH:mm or HH:mm:ss
        r"^\d{4}/\d{2}/\d{2}$",                                                 # YYYY/MM/DD
        r"^\d{2}\.\d{2}\.\d{4}$"                                                # DD.MM.YYYY
    ]
    for pattern in date_patterns:
        if re.match(pattern, value):
            return True
    return False

# Function to determine if an element name should be excluded from pseudonomization
def is_excluded_element(element_name):
    if element_name in EXCLUDED_ELEMENTS:
        return True
    for pattern in EXCLUDED_ELEMENT_PATTERNS:
        if re.match(pattern, element_name):
            return True
    return False

# Function to generate a "similar" value based on character classes and rules
def generate_similar_value(original):
    if original.isdigit():  # All digits
        return ''.join(random.choices(string.digits, k=len(original)))
    elif re.match(r"^\d+\.\d+$", original):  # Digits with a decimal point
        integer, decimal = original.split('.')
        return ''.join(random.choices(string.digits, k=len(integer))) + "." + ''.join(random.choices(string.digits, k=len(decimal)))
    elif re.match(r"^[\w\s,.]+$", original):  # Only letters, numbers, spaces, commas, and periods
        pseudonomized = []
        for char in original:
            if char.isalpha():
                pseudonomized.append(random.choice(string.ascii_letters))  # Random letter
            elif char.isdigit():
                pseudonomized.append(random.choice(string.digits))         # Random digit
            elif char in "., ":
                pseudonomized.append(char)  # Keep punctuation and spaces
        return ''.join(pseudonomized)
    else:
        # Default mixed pseudonomization while excluding special symbols like '>'
        pseudonomized = []
        for char in original:
            if char.isdigit():
                pseudonomized.append(random.choice(string.digits))
            elif char.isalpha():
                pseudonomized.append(random.choice(string.ascii_letters))
            elif char in "., ":
                pseudonomized.append(char)  # Keep punctuation and spaces
            else:
                pseudonomized.append(random.choice(string.ascii_letters + string.digits))
        return ''.join(pseudonomized)

# Function to create consistent pseudonomized values
def pseudonomize_value(value):
    if value not in pseudonomization_map:
        # Check if the value is a date or time; if so, preserve it
        if is_date_or_time(value):
            pseudonomization_map[value] = value
        else:
            # Generate an pseudonomized value that preserves the character class and rules
            pseudonomization_map[value] = generate_similar_value(value)
    return pseudonomization_map[value]

# Recursive function to process and pseudonomize XML elements
def pseudonomize_element(element, parent_excluded=False):
    # Determine if the current element or its children should not be pseudonomized
    element_excluded = parent_excluded or is_excluded_element(element.tag)

    if not element_excluded and element.text and element.text.strip():
        element.text = pseudonomize_value(element.text.strip())

# not need to pseudomymize any attributes
#    for attr_name in element.attrib.keys():  # pseudonomize attributes only if the element is not excluded
#        if not element_excluded:
#            element.attrib[attr_name] = pseudonomize_value(element.attrib[attr_name])

    for child in element:  # Recursively process child elements
        pseudonomize_element(child, parent_excluded=element_excluded)

def pseudonymize_edi_files(xml_directory, pseudonomization_map):
    # Get all .edi files in the same directory
    edi_files = [f for f in os.listdir(xml_directory) if f.endswith('.edi')]

    for edi_file in edi_files:
        edi_file_path = os.path.join(xml_directory, edi_file)

        # Read the content of the .edi file
        with open(edi_file_path, 'r', encoding='utf-8') as file:
            edi_content = file.read()

        pseudonomized_edi_content = edi_content

        # Apply pseudonomization_map: search and replace each item in the .edi file
        for key, pseudonym in pseudonomization_map.items():
            # Replace exact matches of the key with pseudonym in the EDI file
            # Escape any special characters in the key for regex
            pseudonomized_edi_content = re.sub(re.escape(key), pseudonym, pseudonomized_edi_content)

        # Generate pseudonomized file path for the new EDI file
        pseudonomized_edi_file_path = os.path.join(
            xml_directory, f"pseudonomized_{edi_file}"
        )

        # Save the pseudonymized content to a new .edi file
        with open(pseudonomized_edi_file_path, 'w', encoding='utf-8') as file:
            file.write(pseudonomized_edi_content)

        print(f"Pseudonymized .edi file written to {pseudonomized_edi_file_path}")


def save_pseudonomization_map(output_directory, pseudonomization_map):
    """
    Save the pseudonomization_map to a JSON file for inspection.
    :param pseudonomization_map: Dictionary containing the mapping from original to pseudonymized values
    :param output_directory: Directory where the map file should be saved
    """
    # Define the file path for saving the map
    map_file_path = os.path.join(output_directory, "pseudonomization_map.json")

    # Save the pseudonomization_map as a JSON file
    with open(map_file_path, 'w', encoding='utf-8') as file:
        json.dump(pseudonomization_map, file, indent=4, ensure_ascii=False)

    print(f"Pseudonomization map has been written to {map_file_path}")


# Process all XML files in the directory
for filename in os.listdir(xml_directory):  # Iterate through all files in the directory
    if filename.endswith(".xml"):
        file_path = os.path.join(xml_directory, filename)

        # Parse the XML file
        tree = ET.parse(file_path)
        root = tree.getroot()

        # pseudonomize the XML
        pseudonomize_element(root)

        # Save the pseudonomized XML back to a new file
        pseudonomized_file_path = os.path.join(xml_directory, f"pseudonomized_{filename}")
        tree.write(pseudonomized_file_path, encoding="utf-8", xml_declaration=True)

        print(f"pseudonomized XML-file saved to: {pseudonomized_file_path}")

print("Pseudonomization complete for all XML-files.")


pseudonymize_edi_files(xml_directory, pseudonomization_map)
print("Pseudonomization complete for all EDI-files.")

save_pseudonomization_map(xml_directory, pseudonomization_map)