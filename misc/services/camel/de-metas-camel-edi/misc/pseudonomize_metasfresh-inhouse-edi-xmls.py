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

#
# Quick&Dirty-Tool to prepare XML and EDI files from production so that they can be used for regression testing.
#
# !!! The tool is a work in progress (I hope it won't slap you in the face though) !!!
# !!! Vigorously check the results and expect that you'll have to tweak the script !!!
#
# The script iterates all (metasfresh inhouse EDI) xml-files in a given folder and scrambles the sensitive data.
# Each scrambled string is stored in a map, so if e.g. a sensitive GLN occurs at many places, 
# it will always be scrambled to the same output-string.
# 
# After having iterated the folder's XML files, it iterates the folder's EDI files and basically does the same with them.
# 
# The results are stored in a subfolder of the input-folder that is named "pseudonomized"
#

import json  # Needed for saving the anonymization_map in a readable format
import os
import random
import re
import string
import xml.etree.ElementTree as ET

# Directory containing XML and EDI files
#input_directory = "path/to/xml/files"  # Update this path
input_directory= r"C:\work\new_dawn_uat\metasfresh\misc\services\camel\de-metas-camel-edi\src\test\resources\de\metas\edi\esb\invoicexport\edifact_original_files"

# prepare the output folder
output_directory = os.path.join(input_directory, "pseudonomized")
os.makedirs(output_directory, exist_ok=True)

# Dictionary to maintain consistent pseudonomization
pseudonomization_map = {}

# Elements to exclude from pseudonomization
EXCLUDED_ELEMENT_NAMES = {
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
    if element_name in EXCLUDED_ELEMENT_NAMES:
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
                # Preserve case: use random.choice for letters but match case
                if char.islower():
                    pseudonomized.append(random.choice(string.ascii_lowercase))
                else:
                    pseudonomized.append(random.choice(string.ascii_uppercase))
            elif char.isdigit():
                pseudonomized.append(random.choice(string.digits))  # Random digit
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
                if char.islower():
                    pseudonomized.append(random.choice(string.ascii_lowercase))
                else:
                    pseudonomized.append(random.choice(string.ascii_uppercase))
            elif char in ".,@ ":
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

def pseudonymize_edi_files(input_directory, output_directory):
    # Get all .edi files in the same directory
    edi_files = [f for f in os.listdir(input_directory) if f.endswith('.edi')]

    # Define replacement rules
    replacement_rules = [

        (r'UNB\+([^\+\':]+):([^\+\':]+)\+([^\+\':]+):([^\+\':]+)\+([^\+\':]+):([^\+\':]+)\+([^\+\':]+):([^\+\':]+)\+([^\+\':]+)\+([^\+\':]*)\+([^\+\':]+)\+([^\+\':]*)\+([^\+\':]*)\+EANCOM(\d{13})(\d{13})([^\+\':]+)',
         lambda m: f"UNB+{m.group(1)}:{m.group(2)}+{pseudonomize_value(m.group(3))}:{m.group(4)}"
                   f"+{pseudonomize_value(m.group(5))}:{m.group(6)}"
                   f"+{m.group(7)}:{m.group(8)}+{m.group(9)}"
                   f"+{m.group(10) if m.group(10) else ''}+{m.group(11)}"
                   f"+{m.group(12) if m.group(12) else ''}"
                   f"+{m.group(13) if m.group(13) else ''}"
                   f"+EANCOM{pseudonomize_value(m.group(14))}{pseudonomize_value(m.group(15))}{m.group(16)}"),

        # Rule for UNH (message header)
        (r'UNH\+([^\+]+)\+([^\+]+):([^\+]+):(.*)',
         lambda m: f"UNH+{m.group(1)}+{m.group(2)}:{m.group(3)}:{m.group(4)}"),

        # Rule for BGM (message beginning)
        (r'BGM\+([^\+]+)\+([^\+]+)\+([^\+\']+)',
         lambda m: f"BGM+{m.group(1)}+{pseudonomize_value(m.group(2))}+{m.group(3)}"),

        # Rule for DTM (dates/times)
        (r'DTM\+(\d+):(\d+):([^\+\']+)',
         lambda m: f"DTM+{m.group(1)}:{m.group(2)}:{m.group(3)}"),

        # Rule for IMD (item description)
        (r'IMD\+([A-Z])\+([^\+\':]*)\+([^\+\':]*):([^\+\':]*):([^\+\':]*):([^\+\':]*):([^\+\':]*):([^\+\':]*)',
         lambda m: f"IMD+{m.group(1)}+{m.group(2)}+{m.group(3)}:{m.group(4)}:{m.group(5)}:{pseudonomize_value(m.group(6))}:{pseudonomize_value(m.group(7))}:{m.group(8)}"),

        # Rule for NAD (name and address)
        (r'NAD\+([A-Z]{2})\+(\d+)::9\+\+([^+]+)\+([^+]+)\+([^+]+)\+\+(\d+)\+([A-Z]{2})',
         lambda m: f"NAD+{m.group(1)}+{pseudonomize_value(m.group(2))}::9++"
                   f"{pseudonomize_value(m.group(3))}+{pseudonomize_value(m.group(4))}+{pseudonomize_value(m.group(5))}++"
                   f"{pseudonomize_value(m.group(6))}+{pseudonomize_value(m.group(7))}"),

        (r'RFF\+([^\+\':]+):([^\+\':]+)(:([^\+\':]+))?',
         lambda m: f"RFF+{m.group(1)}:{pseudonomize_value(m.group(2))}{m.group(3) if m.group(3) else ''}"),

        # Rule 1 for COM
        (r'COM\+([^\+]+):([^\+\']+)',
         lambda m: f"COM+{pseudonomize_value(m.group(1))}:{m.group(2)}"),

        # Rule 2 for COM
        (r'COM\+\?\+([^\+]+):([^\+\']+)',
         lambda m: f"COM+?+{pseudonomize_value(m.group(1))}:{m.group(2)}"),

        # Rule for CUX (currencies)
        (r'CUX\+([^\+]+)\+([A-Z]{3})\+([^\+]+):(.*)',
         lambda m: f"CUX+{pseudonomize_value(m.group(1))}+{pseudonomize_value(m.group(2))}+"
                   f"{pseudonomize_value(m.group(3))}:{pseudonomize_value(m.group(4))}"),

        # Rule for PAT (payment terms)
        (r'PAT\+([^\+]+)\+([^\+\']*)',
         lambda m: f"PAT+{m.group(1)}+{m.group(2)}"),

        # Rule for MOA (monetary amounts)
        (r'MOA\+([^\+]+):([\d\.]+)',
         lambda m: f"MOA+{m.group(1)}:{pseudonomize_value(m.group(2))}"),

        # Rule for TAX (tax details)
        (r'TAX\+([^\+]+)\+([^\+]+)\+\+([^\+]+)\+\+([^\+]+)',
         lambda m: f"TAX+{pseudonomize_value(m.group(1))}+{pseudonomize_value(m.group(2))}++"
                   f"{pseudonomize_value(m.group(3))}++{pseudonomize_value(m.group(4))}"),

        # Rule for FTX (free-text information)
        (r'FTX\+([^\+]+)\+([^\+]+)\+\+([^\+]+):([^\+]+):(.+)',
         lambda m: f"FTX+{pseudonomize_value(m.group(1))}+{pseudonomize_value(m.group(2))}++"
                   f"{pseudonomize_value(m.group(3))}:{pseudonomize_value(m.group(4))}:{pseudonomize_value(m.group(5))}"),

        # Rule for LIN (lines and items)
        (r'LIN\+(\d*)\+(\d+)\+([^:]+):(.*)',
         lambda m: f"LIN+{pseudonomize_value(m.group(1))}+{pseudonomize_value(m.group(2))}+"
                   f"{pseudonomize_value(m.group(3))}:{pseudonomize_value(m.group(4))}"),

        # Rule for PRI (price details)
        (r'PRI\+([^\+]+)\+([\d\.]+)',
         lambda m: f"PRI+{pseudonomize_value(m.group(1))}+{pseudonomize_value(m.group(2))}"),

        # Rule for PAC (package details)
        (r'PAC\+(\d+)\+([^\+\']+)',
         lambda m: f"PAC+{pseudonomize_value(m.group(1))}+{pseudonomize_value(m.group(2))}"),

        # Rule for CTA
        (r'CTA\+([A-Z]{2})\+([^\:]*):([^\+\']+)',
         lambda m: f"CTA+{m.group(1)}+{pseudonomize_value(m.group(2))}:{pseudonomize_value(m.group(3))}"),

        # Rule for UNT (message trailer)
        (r'UNT\+(\d+)\+([^\+\']+)',
         lambda m: f"UNT+{m.group(1)}+{m.group(2)}"),
    ]

    for edi_file in edi_files:
        edi_file_path = os.path.join(input_directory, edi_file)
        with open(edi_file_path, 'r', encoding='utf-8') as file:
            edi_content = file.read()

        # Apply each replacement rule
        for pattern, replacement_func in replacement_rules:
            edi_content = re.sub(pattern, replacement_func, edi_content)

        pseudonomized_edi_file_path = os.path.join(output_directory, edi_file)
        with open(pseudonomized_edi_file_path, 'w', encoding='utf-8') as file:
            file.write(edi_content)

        print(f"Pseudonymized .edi file saved to {pseudonomized_edi_file_path}")


def save_pseudonomization_map(output_directory):
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


def pseudonymize_xml_files(input_directory, output_directory):
    # Process all XML files in the directory
    for filename in os.listdir(input_directory):  # Iterate through all files in the directory
        if filename.endswith(".xml"):
            file_path = os.path.join(input_directory, filename)

            # Parse the XML file
            tree = ET.parse(file_path)
            root = tree.getroot()

            # pseudonomize the XML
            pseudonomize_element(root)

            # Save the pseudonomized XML back to a new file
            pseudonomized_file_path = os.path.join(output_directory, filename)
            tree.write(pseudonomized_file_path, encoding="utf-8", xml_declaration=True)

            print(f"pseudonomized XML-file saved to: {pseudonomized_file_path}")

pseudonymize_xml_files(input_directory, output_directory)
print("Pseudonomization complete for all XML-files.")


pseudonymize_edi_files(input_directory, output_directory)
print("Pseudonomization complete for all EDI-files.")

save_pseudonomization_map(output_directory)
